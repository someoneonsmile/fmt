package com.example.file.resolver;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;
import org.springframework.web.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author wangyp
 */
@Slf4j
@Component
public class FileArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter( MethodParameter parameter ) {
        Upload file = parameter.getParameterAnnotation(Upload.class);
        return file != null && StringUtils.hasText(file.value());
    }


    @Override
    public Object resolveArgument( MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory ) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        boolean isMultipart = MultipartResolutionDelegate.isMultipartRequest(servletRequest);
        if ( !isMultipart ) {
            return null;
        }
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);

        String name = parameter.getParameterAnnotation(Upload.class).value();
        String path = multipartRequest.getSession().getServletContext().getRealPath("upload") + getDatePath();

        List<MultipartFile> files = multipartRequest.getFiles(name);
        Object result = files.stream().map(( file ) -> {
            File dir = new File(path);
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
            try {
                if ( !dir.exists() ) {
                    boolean mkdirs = dir.mkdirs();
                }
                Thumbnails.of(ImageIO.read(file.getInputStream())).scale(1.0).outputQuality(0.8).toFile(path + fileName);
            } catch ( IOException e ) {
                log.error(file.getOriginalFilename() + ": 上传失败", e);
            }
            return path + fileName;
        }).collect(Collectors.toList());

        WebDataBinder binder = binderFactory.createBinder(webRequest, null, name);
        try {
            result = binder.convertIfNecessary(result, parameter.getParameterType(), parameter);
        } catch ( ConversionNotSupportedException ex ) {
            throw new MethodArgumentConversionNotSupportedException(result, ex.getRequiredType(),
                name, parameter, ex.getCause());
        } catch ( TypeMismatchException ex ) {
            throw new MethodArgumentTypeMismatchException(result, ex.getRequiredType(),
                name, parameter, ex.getCause());
        }
        return result;
    }


    /**
     * 日期路径
     */
    private String getDatePath() {
        LocalDate now = LocalDate.now();
        return File.separator + now.getYear() +
            File.separator + now.getMonth().getValue() +
            File.separator + now.getDayOfMonth() +
            File.separator;
    }
}

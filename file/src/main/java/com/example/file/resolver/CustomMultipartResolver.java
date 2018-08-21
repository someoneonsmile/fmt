package com.example.file.resolver;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wangyp
 */
@Slf4j
@Component( "multipartResolver" )
public class CustomMultipartResolver extends CommonsMultipartResolver {

    private FileUploadProgressListener progressListener;


    public CustomMultipartResolver( FileUploadProgressListener progressListener ) {
        this.progressListener = progressListener;
    }


    @Override
    public MultipartParsingResult parseRequest( HttpServletRequest request )
        throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        progressListener.setSession(request.getSession());
        fileUpload.setProgressListener(progressListener);
        try {
            List<FileItem> fileItems = ( ( ServletFileUpload ) fileUpload ).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch ( FileUploadBase.SizeLimitExceededException ex ) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch ( FileUploadException ex ) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }
}


@Slf4j
@Component
class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;


    void setSession( HttpSession session ) {
        this.session = session;
        Progress progress = new Progress();
        session.setAttribute("progress", progress);
    }


    @Override
    public void update( long pBytesRead, long pContentLength, int pItems ) {
        Progress progress = ( Progress ) session.getAttribute("progress");
        progress.setPBytesRead(pBytesRead);
        progress.setPContentLength(pContentLength);
        progress.setPItems(pItems);
        log.info(JSONObject.toJSONString(progress));
    }
}


@Data
class Progress {

    /**
     * 当前 item 已经读取的字节数
     */
    private long pBytesRead;

    /**
     * 当前 item 的总大小
     */
    private long pContentLength;

    /**
     * 第几个 item
     */
    private long pItems;
}
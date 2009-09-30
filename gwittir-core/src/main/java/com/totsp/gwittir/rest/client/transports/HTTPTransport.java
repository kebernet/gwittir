/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client.transports;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.totsp.gwittir.rest.client.Transport;
/**
 *
 * @author kebernet
 */
public abstract class HTTPTransport implements Transport {

    private PreHook pre;

    private PostHook post;

    /**
     * @return the pre
     */
    public PreHook getPre() {
        return pre;
    }

    /**
     * @param pre the pre to set
     */
    public void setPre(PreHook pre) {
        this.pre = pre;
    }

    /**
     * @return the post
     */
    public PostHook getPost() {
        return post;
    }

    /**
     * @param post the post to set
     */
    public void setPost(PostHook post) {
        this.post = post;
    }


    protected XHRRequestControl doRequest(RequestBuilder builder, final RequestCallback callback){
        doPreHook(builder);
        RequestCallback innerCallback = new RequestCallback(){

            public void onResponseReceived(Request request, Response response) {
                 response = doPostHook(response);
                 callback.onResponseReceived(request, response);
            }

            public void onError(Request request, Throwable exception) {
                callback.onError(request, exception);
            }

        };
        builder.setCallback(innerCallback);
        try{
            return new XHRRequestControl(builder.send());
        } catch(RequestException e){
            callback.onError(null, e);
            return new XHRRequestControl(null);
        }
    }


    protected RequestBuilder doPreHook(RequestBuilder builder){
        return this.pre == null ? builder : this.pre.beforeSend(builder);
    }

    protected Response doPostHook(Response response){
        return this.post == null ? response : this.post.afterReceive(response);
    }



    public static interface PreHook {
        public RequestBuilder beforeSend(RequestBuilder builder);
    }

    public static interface PostHook {
        public Response afterReceive(Response response);
    }

    protected static class XHRRequestControl implements RequestControl{
        private Request request;
        public XHRRequestControl(Request request){
            this.request = request;
        }

        public void cancel() {
            if(this.request != null )
                this.request.cancel();
        }
    }

}

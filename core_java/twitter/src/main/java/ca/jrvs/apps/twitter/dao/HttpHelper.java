package ca.jrvs.apps.twitter.dao;

import java.net.URI;
import org.apache.http.HttpResponse;

public interface HttpHelper {

  /**
   * Execute a HTTP Post call
   * @param uri
   * @return
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a HTTP Get call
   * @param uri
   * @return
   */
  HttpResponse httpGet(URI uri);

  HttpResponse httpDelete(URI uri);
}

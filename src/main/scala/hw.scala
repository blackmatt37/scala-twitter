import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import java.io._
//import org.apache.commons.io.IOUtils


object TwitterPull {

      val AccessToken = "248376792-oW5MiKEQ7s3FRI8OuUyO9IRbO3nyz8ax5yI1Wk5Y";
      val AccessSecret = "OxOwhqluSZBIDK6grze53lqCstskaSzxGAOJJPd6A";
      val ConsumerKey = "oTyJaBUHR2uOccffgjq1sA";
      val ConsumerSecret = "eP48ktb201XqviCKwbXN8wRlrwgBnotwO3iYmjgp1zY";


  def main(args: Array[String]) {

     val consumer = new CommonsHttpOAuthConsumer(ConsumerKey,ConsumerSecret);
     consumer.setTokenWithSecret(AccessToken, AccessSecret);

     val request = new HttpGet("https://stream.twitter.com/1.1/statuses/sample.json");
     consumer.sign(request);
     val client = new DefaultHttpClient();
     val response = client.execute(request);
     println(response.getEntity().isStreaming())
     println(response.getStatusLine().getStatusCode());
     //lazy val list = read(response.getEntity().getContent());
     //lazy val length = list prefixLength (_ != '\n')
     val in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
     def read(s: BufferedReader): Stream[String] = {
        s.readLine() #:: read(s)
    }
    disp(read(in))
    //println(length)
    //disp(list)
     def disp(s: Stream[String]): Int = {
        println(s apply 1)
        disp(s drop 1)
    }
  }
}

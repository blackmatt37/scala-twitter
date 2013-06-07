import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import java.io._
import scala.util.parsing.json._
import org.apache.http.params.BasicHttpParams


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
     val params: BasicHttpParams = new BasicHttpParams()
     val client = new DefaultHttpClient()
     params.setParameter("q","a")
     request.setParams(params)
     val response = client.execute(request)
     println(response.getEntity().isStreaming())
     println(response.getStatusLine().getStatusCode());
     val in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
     def read(s: BufferedReader): Stream[Map[String, String]] = {
        val current = s.readLine()
        def getMap(s: String): Map[String, String] = JSON.parseFull(s).get.asInstanceOf[Map[String, String]]
        val json = getMap(current).withDefaultValue("")
        json #:: read(s)//current
    }
    disp(read(in))
     def disp(s: Stream[Map[String, String]]): Int = {
        println((s apply 1)("text"))
        disp(s drop 1)
    }
  }
}

import org.apache.http.client.ResponseHandler
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import net.liftweb.json._

  
case class Measurement(key: String, `val`: Double, frmt_val: String)
case class Project(id: Long, key: String, name: String, scope: String, qualifier: String,
                   date: java.util.Date, lname: String, lang: String, version: String, 
                   description: Option[String], msr: List[Measurement])

object SonarClient {

	implicit val formats = new DefaultFormats {
		override def dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
	}
   
    def main(args: Array[String]) {
        val json = JsonParser.parse(getMetricsAsJson)
        val projects = json.extract[List[Project]]
        println(projects)
    }
  
    def getMetricsAsJson: String = {
        // return exampleSonarResponse
      
        val url = "http://nemo.sonarsource.org/api/resources?format=json&metrics=ncloc,functions,complexity,nloc";
    
        val httpclient = new DefaultHttpClient
        try {
            val request = new HttpGet(url)

            val responseHandler = new BasicResponseHandler
            val responseBody = httpclient.execute(request, responseHandler)

            responseBody
    	
        } finally {
    	    httpclient.getConnectionManager.shutdown
        }
    
    }
    
    val exampleSonarResponse = """
[{ 
  "id":146590,
  "key":"org.apache.camel:camel",
  "name":"Camel",
  "scope":"PRJ",
  "qualifier":"TRK",
  "date":"2011-10-08T00:47:59+0000",
  "lname":"Camel",
  "lang":"java",
  "version":"2.9-SNAPSHOT",
  "description":"Camel build POM",
  "msr":[
    {"key":"ncloc","val":157998.0,"frmt_val":"157,998"},
    {"key":"functions","val":14727.0,"frmt_val":"14,727"},
    {"key":"complexity","val":35225.0,"frmt_val":"35,225"}
  ]
}]
"""
  
}
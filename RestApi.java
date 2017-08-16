package restApiwithselenium;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import testNG.Exceloperations;
import excelResults.ExcelGenerate;

public class RestApi
  {
	 Exceloperations Excelop=new Exceloperations();
	 StringBuilder sb = new StringBuilder();
	 static String  driverPath = "../AutomationOptimization/Utilities/";
	 public  WebDriver driver;
	 public static void main(String[]args) throws Exception
	  {
		 RestApi api=new RestApi();
		 api.LaunchGUI();
		 api.Restjob();
		 api.GUIoperation();
	  }
	public void LaunchGUI()
	  {
		String url="file:///C:/Users/MMakkenna/Desktop/RestApi.html";
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver", driverPath +"chromedriver.exe");
		driver = new ChromeDriver(options);//launching chrome driver
		driver.manage().window().maximize();
		driver.get(url);//launch the html url
		driver.findElement(By.id("Field 1")).sendKeys(Excelop.getExceldata("..//AutomationOptimization//TestData//RestApiTestData.xlsx","Sheet1",1,1));//entering the first input parameter in GUI
		driver.findElement(By.id("Field 2")).sendKeys(Excelop.getExceldata("..//AutomationOptimization//TestData//RestApiTestData.xlsx","Sheet1",1,2));//entering the second input parameter in GUI
		driver.findElement(By.xpath("html/body/h1/span/form/textarea")).sendKeys(Excelop.getExceldata("..//AutomationOptimization//TestData//RestApiTestData.xlsx","Sheet1",1,0));//entering the Rest api end point url
	  }
	
	public  String Restjob()
	 {
	 try
	 {
	System.out.println("Triggering REST API");
	@SuppressWarnings("resource")
	DefaultHttpClient httpClient = new DefaultHttpClient();//craeting http client to execute the Rest Api end point
	HttpGet getRequest = new HttpGet((Excelop.getExceldata("..//AutomationOptimization//TestData//RestApiTestData.xlsx","Sheet1",1,0)));//passing the GET Method rest api endpoint
//	StringEntity inputs = new StringEntity((Getdatafrompropfile("inputparameters","config.properties")));
//	inputs.setContentType("application/json");
//	postRequest.setEntity(inputs);
	HttpResponse response = httpClient.execute(getRequest);//getting the response back from rest api 
	BufferedReader br = new BufferedReader(
	new InputStreamReader((response.getEntity().getContent())));
		System.out.println("Output from server .... \n");
		for (int c; (c = br.read()) != -1;) {
            sb.append((char) c);                 //reading the output response using buffer reader and string builder
		}
	
	System.out.println("Rest Job Triggered Successfully");
	System.out.println(sb);
	httpClient.getConnectionManager().shutdown();
     } 
		catch (MalformedURLException e)
		 {
		  e.printStackTrace();
		 }
			catch (IOException e)
			  {
			  e.printStackTrace();
			  }
	 
	return sb.toString();
	 }	
	public void getscreenshot() throws Exception //to generate the screenshots
     {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.txt'").format(new Date());
            FileUtils.copyFile(scrFile, new File(".\\Screenshots\\screenshot"+ filename +".png"));
     }
	public void GUIoperation() throws Exception//to wrrite the respponse back to the GUI
	{
		driver.findElement(By.xpath("html/body/h1/span/form/span/span/textarea")).sendKeys(sb);
		driver.findElement(By.xpath("html/body/h1/span/form/span/span/span/span/input")).click();
		getscreenshot();
		driver.close();
	 }
	
	

 }



	



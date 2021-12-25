package brokenlinks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		
		List<WebElement> links= driver.findElements(By.tagName("a"));
		
		System.out.println("Total No of links are:"+ links.size());
		
		List<String> urlList= new ArrayList<String>();
		
		for(WebElement e:links)
		{
			String url =e.getAttribute("href");
			
			urlList.add(url);
			//brokenLink(url);
			
		}
		Long startTime = System.currentTimeMillis();
		urlList.parallelStream().forEach(e -> brokenLink(e));
		Long endTime = System.currentTimeMillis();
		System.out.println("total time taken"+(startTime-endTime));
		
		driver.quit();
}
	
	public static void brokenLink(String linkurl)
	{
		try {
			 URL url= new URL(linkurl);
			 HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
			 
			 httpurlconnection.setConnectTimeout(5000);
			 httpurlconnection.connect();
			 
			 if(httpurlconnection.getResponseCode()>=400)
			 {
				 System.out.println(linkurl+"---------->"+httpurlconnection.getResponseMessage()+" is a broken link");
			 }
			 else
			 {
				 System.out.println(linkurl+"---------->"+httpurlconnection.getResponseMessage());
			 }
			
		}
		catch(Exception e)
		{
			
		}
	}
	
}
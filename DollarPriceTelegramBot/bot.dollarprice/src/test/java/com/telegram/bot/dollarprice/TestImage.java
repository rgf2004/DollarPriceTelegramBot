package com.telegram.bot.dollarprice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telegram.bot.dollarprice.constants.DollarPriceConstants;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import gui.ava.html.image.generator.HtmlImageGenerator;

@Configuration
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestImage {

	@Autowired
	DollarPriceApi dollarPriceApi;
	
	@Test
	public void testFreeMarker()
	{
		freemarker.template.Configuration cfg = new freemarker.template.Configuration();
		try {
			
			Thread.sleep(3000);
			
			Template template = cfg.getTemplate("templates/currencyDetails.ftl");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "Hello World!");

			//List parsing 
			List<String> countries = new ArrayList<String>();
			countries.add("India");
			countries.add("United States");
			countries.add("Germany");
			countries.add("France");
			
			data.put("countries", countries);

			SimpleDateFormat sdf = new SimpleDateFormat( dollarPriceApi.getConfig().getProperty(DollarPriceConstants.DATE_FORMAT_KEY));
			
			
			data.put("dollar_price_label", dollarPriceApi.getConfig().getProperty(DollarPriceConstants.HTML_DOLLAR_PRICE_LABEL));
			data.put("latest_update_date_label", dollarPriceApi.getConfig().getProperty(DollarPriceConstants.HTML_LATEST_UPDATE_DATE));
			//data.put("latest_update_date",sdf.format(dollarPriceApi.getCurrencyDetails().getLastUpdate()));
			
			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();
			
			StringWriter strWriter = new StringWriter();
			template.process(data, strWriter);
			
			
			HtmlImageGenerator htmlImageGenerator = new HtmlImageGenerator();
			
			htmlImageGenerator.loadHtml(strWriter.toString());
			
			htmlImageGenerator.saveAsImage("C:\\png\\test_2.png");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	
	
	public void checkConvertHtmlToImage2() throws InterruptedException
	{
		Thread.sleep(4000);
		
	    String html = "<html>" +
	    				"	<head>" +
			    				"<style> " + 
			    					"body {background-color: #FFFFFF;}"+
			    				"</style>" +
			    			"</head>" + 
			    			"<body>" +
			    				"	<div>" + 
			    				"		<div align=\"right\">" + 
			    				"			<h1 style=\"font-family:Tahoma;font-weight: 450;font-size: 18px;\">سعر الدولار الآن</h1>" + 
			    				"			<h5 style=\"font-family:Tahoma;font-weight: 350;font-size: 12px;\">اخر تحديث 02016-11-22 11:44:37</h5>" +
			    				"		</div> " +
			    				"		<div> " +
			    				"			<table width=\"500\" border=\"1\">" + 
			    				"				<tr>" +
			    				"					<td align=\"center\">البنك</td> " +
			    				"				<td align=\"center\">سعر الشراء</td> " +
			    				"					<td align=\"center\">سعر البيع</td> " +
			    				"				</tr>" +
			    				"				<tr>" +
			    				"					<td align=\"right\">البنك اﻷهلي الكويتي</td> "+ 
			    				"					<td align=\"center\">17.400000</td>" +
			    				"					<td align=\"center\">17.800000</td>" +
			    				"				</tr>" +			    	
			    				"			</table> "+
			    				"		</div>" +
			    				"	</div>" +
			    				"	</body>"+
			    				"</html>";

		
		
		
	}
	
	
	public void checkConvertHtmlToImage() throws InterruptedException
	{
		
			Thread.sleep(5000);
		    		   
			String html = "";//dollarPriceApi.getCurrencyQueryResultAsHtml();

		    System.out.println(html);
			
		    JLabel label = new JLabel(html);
		    label.setSize(600, 1000);

		    BufferedImage image = new BufferedImage(
		            label.getWidth(), label.getHeight(), 
		            BufferedImage.TYPE_INT_ARGB);

		    {
		        // paint the html to an image
		        Graphics g = image.getGraphics();
		        g.setColor(Color.BLACK);
		        label.paint(g);
		        g.dispose();
		    }

		    // get the byte array of the image (as jpeg)
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    try {
				ImageIO.write(image, "jpg", baos);
				ImageIO.write(image, "png", new File("c:\\test.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    byte[] bytes = baos.toByteArray();
		    
		
	}
	
}

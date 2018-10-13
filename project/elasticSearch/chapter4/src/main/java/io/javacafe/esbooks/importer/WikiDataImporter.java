package io.javacafe.esbooks.importer;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WikiDataImporter {
	Logger log = LoggerFactory.getLogger(WikiDataImporter.class);
	private String host = "localhost";
	private int port = 9200;
	
	public String getEsURL() {
		return host + ":" + port;
	}
	
	public void setEsURL(String esUrl) {
		if(esUrl.indexOf(":") == -1) {
			return;
		}
		host = esUrl.split(":")[0];
		port = Integer.parseInt(esUrl.split(":")[1]);
	}
	
	public XContentBuilder convertObject(Wiki vo) throws IOException {
		return jsonBuilder().startObject()
				.field("category", vo.getCategory())
				.field("title", vo.getTitle())
				.field("summary", vo.getSummary())
				.field("rgstDttm", vo.getRgstDttm())
				.field("subTitle1", vo.getSubTitle1())
				.field("subTitle2", vo.getSubTitle2())
				.field("subTitle3", vo.getSubTitle3())
				.field("subTitle4", vo.getSubTitle4())
				.field("subTitle5", vo.getSubTitle5())
				.field("subContent1", vo.getSubContent1())
				.field("subContent2", vo.getSubContent1())
				.field("subContent3", vo.getSubContent1())
				.field("subContent4", vo.getSubContent1())
				.field("subContent5", vo.getSubContent1())
				.endObject();
	}
	
	
	
	public void createIndex() throws Exception {
		CreateIndexRequest request = new CreateIndexRequest("wiki");
		request.settings(getSettings());
		request.mapping("doc", getMapping(),XContentType.JSON);
		
		try(RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(host,port,"http")))){
			
			Response response = client.getLowLevelClient().performRequest("HEAD", "/wiki"); 
			
			int statusCode = response.getStatusLine().getStatusCode(); 
			if (statusCode != 404) {
				client.indices().delete(new DeleteIndexRequest("wiki"));
			}
			client.indices().create(request);
		}
	}

	public XContentBuilder getSettings() throws IOException {
		return jsonBuilder().startObject()
				.startObject("index")
					.field("number_of_shards",5)
					.field("number_of_replicas", 2)
					.startObject("analysis")
						.startObject("analyzer")
							.startObject("korean")
								.field("type","custom")
								.field("tokenizer","my_nori_tokenizer")
							.endObject()
						.endObject()
						.startObject("tokenizer")
							.startObject("my_nori_tokenizer")
								.field("type","nori_tokenizer")
							.endObject()
						.endObject()	
					.endObject()
				.endObject()
			.endObject();
	}
	
	public String getMapping() throws Exception {
		return Files.readAllLines(Paths.get(getClass().getClassLoader().getResource("mapping.json").toURI())).stream()
				.collect(Collectors.joining());
	}
	
	public void doBulkInsert(File f) {
		try(RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(host,port,"http")));
				FileInputStream excelFile = new FileInputStream(f);) {
			Workbook workbook = null;
			
			if(f.toString().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(excelFile);
			}else if(f.toString().endsWith("xls")){
				workbook = new HSSFWorkbook(excelFile);
			}
			
			Sheet sheet = workbook.getSheetAt(0);
			
			int idx = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			
			BulkRequest bulkRequest = new BulkRequest();
			bulkRequest.timeout(TimeValue.timeValueSeconds(3600));
			System.out.print(String.format("[%s]/%d import start ...", f.getName(), sheet.getLastRowNum()));
			for(Row row : sheet) {
				if(idx++ == 0) {
					continue;
				}
			
				Wiki wiki = new Wiki();
				
				wiki.setCategory(row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setTitle(row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSummary(row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubTitle1(row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubContent1(row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubTitle2(row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubContent2(row.getCell(6,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubTitle3(row.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubContent3(row.getCell(8,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubTitle4(row.getCell(9,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubContent4(row.getCell(10,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubTitle5(row.getCell(11,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setSubContent5(row.getCell(12,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
				wiki.setRgstDttm(sdf.parse(row.getCell(13,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue()));
				
				bulkRequest.add(new IndexRequest("wiki","doc")
						.source(convertObject(wiki)));
				if(idx % 1000 == 0) {
					client.bulk(bulkRequest);
					bulkRequest = new BulkRequest();
					bulkRequest.timeout(TimeValue.timeValueSeconds(3600));
				}
			}
			client.bulk(bulkRequest);
		}catch(Exception ex) {
			log.error("Wiki Data Import 실패 {} / {}",f.getName() , ex);
		}
		System.out.println("finished!!");
	}	
}

package io.pnt.esdataimporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.javacafe.esbooks.importer.ESDataImporterApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={ESDataImporterApplication.class})
public class WikiDataImpoterTests {

	@Test
	public void classpathTest() throws Exception {
//		paths.forEach(p -> {
//			System.out.println(p.getFileName());
//		});
	}
	
//	@Test
//	public void excelToWikiTest() throws Exception {
//		final WikiDataImporter importer = new WikiDataImporter();
//		importer.createIndex();
//		
//		String rootDir = "C:\\Users\\GgamMang\\Google 드라이브\\[집필] ElasticSearch 실전 비급\\03-02-농식품백과사전";
//		
//		try(Stream<Path> paths = Files.walk(Paths.get(rootDir))){
//			List<Wiki> wikiList = 
//				paths.filter(Files::isRegularFile)
//				.filter(p -> {
//					String path = p.toString();
//					if(path.endsWith("xlsx") || path.endsWith("xls")) return true;
//					return false;
//				})
//				.map(p -> {
//					List<Wiki> wikis = null;
//					try {
//						log.info(p.toString());
//						wikis = importer.getWikiesFromFile(p.toFile());
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					return wikis;
//				})
//				.flatMap(list -> list.stream())
//				.collect(Collectors.toList());
//			log.info("Total Size : " + wikiList.size());
//		}
//	}

}

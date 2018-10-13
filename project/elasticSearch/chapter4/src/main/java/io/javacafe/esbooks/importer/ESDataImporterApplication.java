package io.javacafe.esbooks.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class ESDataImporterApplication implements CommandLineRunner {
	
	@Autowired
	WikiDataImporter importer;
	
	public static void main(String[] args) {
		SpringApplication.run(ESDataImporterApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(args.length != 0) {
			importer.setEsURL(args[0]);
		}
		Scanner s = new Scanner(System.in);
		while(true) {
			System.out.println(String.format("----- Data Impoter [%s] -----",importer.getEsURL()));
			System.out.println("1. set ES url");
			System.out.println("2. import wiki data (chapter4)");
			System.out.println("3. exit");
			System.out.println("---------------");
			System.out.print(">>");
			selectMenu(s, s.next());
		}
	}
		
	private void selectMenu(Scanner s, String menuId) throws Exception{
		switch(menuId){
		case "1":
			System.out.print(String.format("input es url current : [%s] >> ",importer.getEsURL()));
			importer.setEsURL(s.next());
			break;
		case "2":
			importer.createIndex();
			try(Stream<Path> paths = Files.walk(Paths.get(new ClassPathResource("data/04-wiki").getURI()))){
				paths.filter(Files::isRegularFile)
				.filter(p -> {
					String path = p.toString();
					if(path.endsWith("xlsx") || path.endsWith("xls")) return true;
					return false;
				})
				.forEach(p -> {
					importer.doBulkInsert(p.toFile());
				});
				
			}
			break;
		case "3":
			System.exit(1);
			break;
		}
	}
}

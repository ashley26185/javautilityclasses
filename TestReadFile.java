import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.charset.CharsetDecoder;
import java.io.*;
import java.nio.*;
import java.nio.charset.CodingErrorAction;
import java.nio.channels.*;
import java.net.URISyntaxException;

public class TestReadFile {

	static int NUM_OF_RECORDS = 0;

	public static void main(String[] args){

		String logDirectory = args[0];
		TestReadFile myObj = new TestReadFile();

		try(Stream<Path> myFiles = Files.list(Paths.get(logDirectory))){
				//myFiles.forEach(System.out::println);
				myFiles.filter( f -> f.toString().endsWith("log")).sorted().forEach( f -> myObj.readMyFileNew(f.toString(),args[1]));

				System.out.println("Total Number of Records = " + NUM_OF_RECORDS );
		} catch (IOException e){
			System.out.println("Inside main exception");
			e.printStackTrace();
		} catch (Exception e){
			System.out.println("Inside main exception");
			e.printStackTrace();
		}

	}


	public static void readMyFile(String fileName , String searchString){

		System.out.println ( "Filename:" + fileName );
		//UTF-8 Charset.forName("US-ASCII") map(str -> {return str.replaceAll("\uC2A0", "");})
		try(Stream<String> stream = Files.lines(Paths.get(fileName),StandardCharsets.UTF_16)){

			System.out.println("Read the file lines successfully");
			List<String> myList = stream
															.map(str -> {	return str.replaceAll("\uC2A0", "");})
																.filter(fileLine -> fileLine.contains(searchString)).collect(Collectors.toList());
			System.out.println("Finished filtering successfully");

			myList.stream().forEach(System.out::println);

			NUM_OF_RECORDS += myList.size();

			System.out.println("Number of Records =" +  myList.size());

		} catch (IOException e){
			System.out.println("readMyFile:" + fileName);
			e.printStackTrace();
		}
	}


	public void readMyFileNew(String fileName , String searchString) {
		CharsetDecoder dec=StandardCharsets.UTF_8.newDecoder()
                  .onMalformedInput(CodingErrorAction.REPLACE);

		System.out.println("Getting the file" + fileName);

		Path path=Paths.get(fileName);

		List<String> lines = null;
		try(Reader r=Channels.newReader(FileChannel.open(path), dec, -1);
		    BufferedReader br=new BufferedReader(r)) {
		        lines=br.lines()
		                .filter(s->!s.contains(dec.replacement()))
		                .filter(fileLine -> fileLine.contains(searchString))
		                .collect(Collectors.toList());
		} catch (IOException e){
			System.out.println("readMyFile:" + fileName);
			e.printStackTrace();
		}

		System.out.println("Finished filtering successfully");

		lines.stream().forEach(System.out::println);

		NUM_OF_RECORDS += lines.size();

		System.out.println("Number of Records =" +  lines.size());


	}

	//Write a method using BufferedReader. https://softwarecave.org/2014/12/18/reading-text-file-line-by-line-in-java/
	


}

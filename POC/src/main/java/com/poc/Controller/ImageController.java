package com.poc.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import com.poc.Model.Example;
import com.poc.Model.ExampleDto;
import com.poc.Model.UploadVideo;
import com.poc.Repo.ExampleRepo;
import com.poc.Repo.VideoRepo;
import com.poc.Services.FileUploadUtil;

@Controller
public class ImageController {
	
	
//	public static String uploadVideoDir = System.getProperty("user.dir") + "/src/main/resources/static/user-photos";
	
	private ExampleRepo exampleRepo;
	
	private VideoRepo videorepo;

public ImageController(ExampleRepo exampleRepo, VideoRepo videorepo) {
		super();
		this.exampleRepo = exampleRepo;
		this.videorepo = videorepo;
	}

//	@Autowired
//	private VideoRepo videoRepo;

	

	@GetMapping("/")
	public String home(Model model) {
		List<Example> example = exampleRepo.findAll();
		List<UploadVideo> video=videorepo.findAll();
		model.addAttribute("example", example);
		return "cameraapp";
	}


	@GetMapping("/index")
	public String index() {

		return "index";
	}

	
	@GetMapping("/video")
	public String video() {

		return "video";
	}

	@GetMapping("/display")
	public String display(Model model) {
		List<Example> example = exampleRepo.findAll();
		List<UploadVideo> video=videorepo.findAll();
		model.addAttribute("example", example);
		model.addAttribute("video", video);
		return "display";
	}
	
	@SuppressWarnings("resource")
	@PostMapping("/image/save")
    public @ResponseBody String uploadImage2(@RequestBody ExampleDto exdto)
    {  
		Example ex=new Example();
        try
        {
        	 Date date = Calendar.getInstance().getTime();  
        	    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        	    String strDate = dateFormat.format(date);  
        	
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte=Base64.decodeBase64(exdto.getPhotos());
//        	    byte[] result = Base64.getDecoder().decode(exdto.getPhotos())
            String uploadDir = "./user-photos/" ;
            String replace = strDate.replace(":",".").replace("{","").replace("}","").replace("/",".").replace(" ","");
            
            String UploadDirTo="./user-photos/"+replace+".png" ;
            ex.setPhotos(UploadDirTo);
             exampleRepo.save(ex);
            Path uploadPath = Paths.get(uploadDir);
	         
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
            new FileOutputStream(UploadDirTo).write(imageByte);
            return "success ";
        }
        catch(Exception e)
        {
            return "error = "+e;
        }

    }
	

//
//	@PostMapping("/image/save")
//	public RedirectView saveUser(Example example, @RequestParam("image") MultipartFile multipartFile)
//			throws IOException {
//		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//		example.setPhotos(fileName);
//		Example savedUser = exampleRepo.save(example);
//		String uploadDir = "./user-photos/" + savedUser.getId();
//	     String path=(uploadDir+fileName);
//		List<String> pathAndName= new ArrayList<>();
//		pathAndName.add(path);
//		//example.setFilePath(pathAndName);
//		System.out.println(pathAndName);
//		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//		return new RedirectView("/display", true);
//	}
	
	@PostMapping("/save/video")
	public RedirectView savevideo(UploadVideo video, @RequestParam("file") MultipartFile file)
			throws IOException {
  
		String videofile = StringUtils.cleanPath(file.getOriginalFilename());
		video.setVideos(videofile);
		UploadVideo savedUser = videorepo.save(video);
		String uploadvideoDir = "./user-video/" + savedUser.getId();
		FileUploadUtil.savevideo(uploadvideoDir, videofile, file);
		return new RedirectView("/display", true);
	}
	
	
	@GetMapping("/delete/{id}")
	public RedirectView delete(@PathVariable long id) {
		
		exampleRepo.deleteById(id);
		return new RedirectView("/display", true);
	}
	
	
	
	
//
//	@PostMapping("/image/save")
//	public RedirectView saveVideo(@RequestParam("image") MultipartFile file) throws IOException {
//
//		Example video = new Example();
//		String imageUUID = file.getOriginalFilename();
//		Path fileNameAndPath = Paths.get(uploadVideoDir, imageUUID);
//		Files.write(fileNameAndPath, file.getBytes());
//		video.setPhotos(imageUUID);
//		exampleRepo.save(video);
//
//		return new RedirectView("/display", true);
//	}

//	    
//    public void saveimage(MultipartFile multipartFile,MultipartFile vedio) {
//  	  
//  	  Example ex=new Example();
//  	  
//  	  String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        String vediofile=StringUtils.cleanPath(vedio.getOriginalFilename());
//        ex.setVedioes(Base64.getEncoder().encodeToString(vediofile.getBytes()));
//        ex.setPhotos((Base64.getEncoder().encodeToString(fileName.getBytes())));
//        exampleRepo.save(ex);
//  	  
//    }
//	 
//	 
//	 

}

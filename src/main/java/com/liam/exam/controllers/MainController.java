package com.liam.exam.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liam.exam.models.LoginUser;
import com.liam.exam.models.Rating;
import com.liam.exam.models.Show;
import com.liam.exam.models.User;
import com.liam.exam.services.RatingService;
import com.liam.exam.services.ShowService;
import com.liam.exam.services.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private ShowService mainServ;
	
	@Autowired
	private RatingService ratingServ;

	


//	  _________              _____ 
//	 /   _____/ ____________/ ____\
//	 \_____  \_/ __ \_  __ \   __\ 
//	 /        \  ___/|  | \/|  |   
//	/_______  /\___  >__|   |__|   
//	        \/     \/              


	
	// DashBoard
	@GetMapping("/dashboard") // All Serfs
	public String index(HttpSession session, Model model, @ModelAttribute("rating") Rating rating) {
		if (session.getAttribute("user_id") == null) {
			return "redirect:/";
		}

		Long userId = (Long) session.getAttribute("user_id");
		User userLog = userServ.getUser(userId);
		model.addAttribute("userLog", userLog);
		
		model.addAttribute("allSerfs", mainServ.getAll());
		
		return "/dashboard.jsp";
	}
	
	
	// Show One Serf
	@GetMapping("/serf/show/{id}")
	public String showOneSerf(@PathVariable("id") Long id, @ModelAttribute("show") Show show, Model model, HttpSession session, @Valid @ModelAttribute("rating") Rating rating, BindingResult result) {
		if (session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		model.addAttribute("score", ratingServ.findAllByOrderByScoreDesc());
		model.addAttribute("userLog", userServ.getUser(userId));
		model.addAttribute("show", mainServ.getOne(id));
		return "/showSerf.jsp";
	}
	


	// Create Serf Form
	@GetMapping("/newSerfForm")
	public String newSerfForm(@ModelAttribute("showForm") Show show, HttpSession session, Model model) {
		if (session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		Long userId = (Long) session.getAttribute("user_id");
		User userLog = userServ.getUser(userId);
		model.addAttribute("userLog", userLog);
		return "/createSerf.jsp";
	}

	// Create Serf Process
	@PostMapping("/create/serf")
	public String createSerf(@Valid @ModelAttribute("showForm") Show newSerf, BindingResult result, HttpSession session, @RequestParam("title") String title) {
		if(mainServ.checkTitle(title) == true) {
			return "/createSerf.jsp";
		}

		if(result.hasErrors()) {
			 return "/createSerf.jsp";
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		newSerf.setOwner(userServ.getUser(userId));
		
		mainServ.createOne(newSerf);
		return "redirect:/dashboard";
	}
	
	
	// Edit Serf Form
	@GetMapping("/edit/serf/{id}")
	public String editSerfForm(@PathVariable("id") Long id, Model model, HttpSession session) {

		if (session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		User userLog = userServ.getUser(userId);
		model.addAttribute("userLog", userLog);
		
		model.addAttribute("show", mainServ.getOne(id));
		return "/editSerf.jsp";
		
	}
	
	// Edit Serf Process
	@PostMapping("/edit/serf/proc/{id}")
	public String updateSerf(@PathVariable("id") Long id, @Valid @ModelAttribute("show") Show serf, BindingResult result, @RequestParam("owner") User user, Model model, HttpSession session, @RequestParam("title") String title) {
		if(mainServ.checkTitle(title) == true) {
			Long userId = (Long) session.getAttribute("user_id");
			User userLog = userServ.getUser(userId);
			
			model.addAttribute("userLog", userLog);
			return "/editSerf.jsp";
		}
		if (result.hasErrors()) {

			Long userId = (Long) session.getAttribute("user_id");
			User userLog = userServ.getUser(userId);
			
			model.addAttribute("userLog", userLog);

			return "/editSerf.jsp";
		}
		serf.setOwner(user);
		mainServ.updateOne(serf);
		return "redirect:/serf/show/{id}";
	}
	

	// Delete Serf
	@GetMapping("/delete/serf/{id}")
	public String deleteSerf(@PathVariable("id") Long id) {
		mainServ.deleteOne(id);
		return "redirect:/dashboard";
	}



//    __  ___                     ___      __  ___                               
//   /  |/  /___ _____  __  __   |__ \    /  |/  /___ _____  __  __     __    __ 
//  / /|_/ / __ `/ __ \/ / / /   __/ /   / /|_/ / __ `/ __ \/ / / /  __/ /___/ /_
// / /  / / /_/ / / / / /_/ /   / __/   / /  / / /_/ / / / / /_/ /  /_  __/_  __/
///_/  /_/\__,_/_/ /_/\__, /   /____/  /_/  /_/\__,_/_/ /_/\__, /    /_/   /_/   
//                   /____/                               /____/                 

	

	// Add Rating
	@PostMapping("/addRating/")
	private String addRating(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, HttpSession session, Model model, @RequestParam("show") Long id) {
		if(result.hasErrors()) {
			Long userId = (Long) session.getAttribute("user_id");
			User userLog = userServ.getUser(userId);
			model.addAttribute("userLog", userLog);
			model.addAttribute("show", mainServ.getOne(id));
			model.addAttribute("allSerfs", mainServ.getAll());
			return "/showSerf.jsp";
		}
		Long userId = (Long) session.getAttribute("user_id");
//		rating.setUserRating(userServ.getUser(userId));
//		rating.setMainRating(mainServ.getOne(id));
		ratingServ.createOne(rating);
		return "redirect:/serf/show/" + id;
		
	}
	
	// Remove Rating
	@GetMapping("/removeRating/{id}")
	private String removeRating(@PathVariable("id") Long id, @RequestParam("show") Show show) {

		Long mainId = show.getId();
		System.out.println(mainId);

		ratingServ.deleteOne(id);
		return "redirect:/serf/show/" + mainId;
		
	}
	

	
//	 ____ ___                    
//	|    |   \______ ___________ 
//	|    |   /  ___// __ \_  __ \
//	|    |  /\___ \\  ___/|  | \/
//	|______//____  >\___  >__|   
//	             \/     \/       

	
	
	//////  NEW LOGIN/REG //////
	
   @GetMapping("/")
   public String loginPage(Model model) {
       model.addAttribute("newUser", new User());
       model.addAttribute("newLogin", new LoginUser());
       return "/login.jsp";
   }
   
   // Create User Process
   @PostMapping("/registerUser")
   public String registerUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {
   	userServ.register(newUser, result);
       if(result.hasErrors()) {
           model.addAttribute("newLogin", new LoginUser());
           return "/login.jsp";
       }
       session.setAttribute("user_id", newUser.getId());
       return "redirect:/dashboard";
   }
   
   // Login User Process
   @PostMapping("/loginUser")
   public String loginUser(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model, HttpSession session) {
       User user = userServ.login(newLogin, result);
       if(result.hasErrors()) {
           model.addAttribute("newUser", new User());
           return "/login.jsp";
       }
       session.setAttribute("user_id", user.getId());
       return "redirect:/dashboard";
   }
   
   // Show One User
   @GetMapping("/user/show/{id}")
   public String showUser(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		model.addAttribute("userLog", userServ.getUser(userId));
   	model.addAttribute("user", userServ.getUser(id));
       return "/showUser.jsp";
   }
   
   // Logout User
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// Delete User
	@GetMapping("/delete/user/{id}")
	public String deleteSUser(@PathVariable("id") Long id) {
		userServ.deleteOne(id);
		return "redirect:/dashboard";
	}

}

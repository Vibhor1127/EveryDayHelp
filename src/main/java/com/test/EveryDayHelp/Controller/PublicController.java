package com.test.EveryDayHelp.Controller;

import com.test.EveryDayHelp.Entity.*;
import com.test.EveryDayHelp.Service.PublicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    // Helper method to check user session
    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("UserKey") != null;
    }

    @ModelAttribute
    public void setTitle(Model model) {
        model.addAttribute("title", "EveryDayHelp");
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "Easy day Help Portal");
        return "public/home";
    }

    @GetMapping("/getstarted")
    public String showGetStartedPage() {
        return "/public/GetStarted";
    }

    @GetMapping("/bookService")
    public String showBookingPage(HttpSession session, RedirectAttributes rd) {
        if (!isUserLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Please login to book services");
            return "redirect:/userLogin";
        }
        return "user/user_bookings";
    }

    @GetMapping("/feedback")
    public String showFeedbackPage(HttpSession session, RedirectAttributes rd) {
        if (!isUserLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Please login to provide feedback");
            return "redirect:/userLogin";
        }
        return "user/Feedback";
    }

    // Keep old mappings for backward compatibility
    @GetMapping("/AllServices")
    public String showAllServicesPage(HttpSession session, RedirectAttributes rd) {
        if (!isUserLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Please login to view services");
            return "redirect:/userLogin";
        }
        return "user/user_bookings";
    }

    @GetMapping("/FeedBack")
    public String FeedBack(HttpSession session, RedirectAttributes rd) {
        if (!isUserLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Please login to provide feedback");
            return "redirect:/userLogin";
        }
        return "user/Feedback";
    }

    @GetMapping("/about")
    public String aboutUsPage() {
        System.out.println("ABOUT PAGE HIT");
        return "public/about_us";
    }

    @GetMapping("/contactUs")
    public String ContactUs() {
        return "public/contact_us";
    }

    @PostMapping("/contactUs")
    public String addContact(RedirectAttributes rda, Contacts contacts) {
        publicService.addContact(contacts);
        rda.addFlashAttribute("msg", "Thank You for contacting us");
        return "redirect:/contactUs";
    }

    @GetMapping("/UserRegistration")
    public String UserRegistration(HttpSession session) {
        // If already logged in, redirect to user home
        if (isUserLoggedIn(session)) {
            return "redirect:/userHome";
        }
        return "user/UserRegistration";
    }

    @PostMapping("/registerUser")
    public String registerUser(RedirectAttributes rda, UserDetails userDetails,
                               @RequestParam("profilepic") MultipartFile file) {
        publicService.registerUser(userDetails, file);
        rda.addFlashAttribute("msg", "Thank You for registering");
        return "redirect:/UserRegistration";
    }

    @GetMapping("/userLogin")
    public String userLoginPage(HttpSession session) {
        // If already logged in, redirect to user home
        if (isUserLoggedIn(session)) {
            return "redirect:/userHome";
        }
        return "user/user_login";
    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session,
                            RedirectAttributes rd) {

        UserDetails userDetails = publicService.UserLogin(email, password);
        if (userDetails != null) {
            session.setAttribute("UserKey", userDetails);
            model.addAttribute("UserInfo", userDetails);

            // Get user's bookings
            List<Booking> bookings = publicService.getUserBookings(userDetails.getEmail());
            model.addAttribute("bookings", bookings);

            // Get all notices for display
            List<AdminNotice> notices = publicService.getAllNotices();
            model.addAttribute("notices", notices);

            return "user/user_home";
        }

        rd.addFlashAttribute("msg", "Invalid Email or Password");
        return "redirect:/userLogin";
    }

    @GetMapping("/userHome")
    public String userHome(HttpSession session, Model model, RedirectAttributes rd) {
        if (!isUserLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/userLogin";
        }

        UserDetails user = (UserDetails) session.getAttribute("UserKey");
        model.addAttribute("UserInfo", user);

        // Get user's bookings
        List<Booking> bookings = publicService.getUserBookings(user.getEmail());
        model.addAttribute("bookings", bookings);

        // Get all notices for display
        List<AdminNotice> notices = publicService.getAllNotices();
        model.addAttribute("notices", notices);

        return "user/user_home";
    }

    @PostMapping("/bookService")
    public String bookService(HttpSession session,
                              @RequestParam String serviceType,
                              @RequestParam String duration,
                              @RequestParam Integer amount,
                              RedirectAttributes rda) {
        if (!isUserLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Please login first");
            return "redirect:/userLogin";
        }

        UserDetails user = (UserDetails) session.getAttribute("UserKey");

        Booking booking = new Booking();
        booking.setServiceType(serviceType);
        booking.setDuration(duration);
        booking.setAmount(amount);
        booking.setEmail(user.getEmail());
        booking.setStatus("Pending");
        booking.setDate(LocalDateTime.now());

        publicService.saveBooking(booking);

        rda.addFlashAttribute("msg", "Service booked successfully! Please wait for admin approval.");
        return "redirect:/userHome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rd) {
        session.removeAttribute("UserKey");
        session.invalidate();
        rd.addFlashAttribute("msg", "Successfully Logged Out");
        return "redirect:/userLogin";
    }

    @PostMapping("/logout")
    public String logoutPost(HttpSession session, RedirectAttributes rd) {
        session.removeAttribute("UserKey");
        session.invalidate();
        rd.addFlashAttribute("msg", "Successfully Logged Out");
        return "redirect:/userLogin";
    }
}
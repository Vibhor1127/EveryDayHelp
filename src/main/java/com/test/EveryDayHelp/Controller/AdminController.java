package com.test.EveryDayHelp.Controller;

import java.util.List;

import com.test.EveryDayHelp.Entity.*;
import com.test.EveryDayHelp.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController
{
    private final AdminService adminService;

    // Helper method to check admin session
    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("adminKey") != null;
    }

    @GetMapping("/adminLogin")
    public String admin(HttpSession session, RedirectAttributes rd)
    {
        // If already logged in, redirect to home
        if (isAdminLoggedIn(session)) {
            return "redirect:/admin/adminHome";
        }
        return "admin/admin_login";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(HttpSession session, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }
        return "admin/add_employee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(HttpSession session, Employee employee, RedirectAttributes rda,
                              @RequestParam("emp_pic") MultipartFile file)
    {
        if (!isAdminLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.addEmployee(employee, file);
        rda.addFlashAttribute("msg", "Registered Successfully");
        return "redirect:/admin/addEmployee";
    }

    @GetMapping("addNotice")
    public String addNotice(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        AdminNotice notice = new AdminNotice();
        model.addAttribute("noticeObj", notice);
        return "admin/admin_notice";
    }

    @PostMapping("postNotice")
    public String postNotice(HttpSession session, @ModelAttribute AdminNotice notice, RedirectAttributes rda)
    {
        if (!isAdminLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.addNotice(notice);
        rda.addFlashAttribute("msg", "Notice Posted Successfully");
        return "redirect:/admin/addNotice";
    }

    @GetMapping("allUsers")
    public String allUsers(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<UserDetails> userList = adminService.allUsers();
        model.addAttribute("users", userList);
        return "admin/Admin_allUsers";
    }

    @GetMapping("allContacts")
    public String allContacts(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<Contacts> contactList = adminService.allContacts();
        model.addAttribute("contacts", contactList);
        return "admin/Admin_allContacts";
    }

    @GetMapping("/allEmployees")
    public String allEmployees(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<Employee> employeeList = adminService.allEmployees();
        model.addAttribute("employees", employeeList);
        return "admin/Admin_allEmployees";
    }

    @GetMapping("/allNotices")
    public String allNotices(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<AdminNotice> NoticeList = adminService.allNotice();
        model.addAttribute("notices", NoticeList);
        return "admin/Admin_ViewNotice";
    }

    @GetMapping("adminHome")
    public String adminHome(HttpSession session, Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        Admin admin = (Admin)session.getAttribute("adminKey");
        model.addAttribute("adminInfo", admin);
        return "admin/admin_home";
    }

    @PostMapping("adminLogin")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password,
                             Model model,
                             HttpSession session,
                             RedirectAttributes rd)
    {
        System.out.println(email + password);
        Admin admin = adminService.adminLogin(email, password);
        System.out.println(admin);

        if (admin != null) {
            session.setAttribute("adminKey", admin);
            model.addAttribute("adminInfo", admin);
            return "admin/admin_home";
        }

        rd.addFlashAttribute("msg", "Invalid Email / Password");
        return "redirect:/admin/adminLogin";
    }

    @GetMapping("adminLogout")
    public String adminLogoutGet(HttpSession session, RedirectAttributes rd)
    {
        session.removeAttribute("adminKey");
        session.invalidate();
        rd.addFlashAttribute("msg", "Successfully Logged Out");
        return "redirect:/admin/adminLogin";
    }

    @PostMapping("adminLogout")
    public String adminLogout(HttpSession session, RedirectAttributes rd)
    {
        session.removeAttribute("adminKey");
        session.invalidate();
        rd.addFlashAttribute("msg", "Successfully Logged Out");
        return "redirect:/admin/adminLogin";
    }

    @GetMapping("deleteUser")
    public String deleteUser(HttpSession session, @RequestParam String email,
                             Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.deleteUser(email);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("deleteContact")
    public String deleteContact(HttpSession session, @RequestParam Integer contact_id,
                                RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.deleteContact(contact_id);
        return "redirect:/admin/allContacts";
    }

    @GetMapping("deleteEmployee")
    public String deleteEmployee(HttpSession session, @RequestParam String id,
                                 RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.deleteEmployee(id);
        return "redirect:/admin/allEmployees";
    }

    @GetMapping("editUser")
    public String editUser(HttpSession session, @RequestParam String email,
                           Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        UserDetails user = adminService.editUser(email);
        model.addAttribute("user", user);
        return "admin/Admin_editUser";
    }

    @PostMapping("editUser")
    public String editUser(HttpSession session, @ModelAttribute UserDetails modifiedUser,
                           Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.editUserFinal(modifiedUser);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("deleteNotice")
    public String deleteNotice(HttpSession session, @RequestParam Integer notice_id,
                               RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.deleteNotice(notice_id);
        return "redirect:/admin/allNotices";
    }

    @GetMapping("editEmployee")
    public String editEmployee(HttpSession session, @RequestParam("id") String empId,
                               Model model, RedirectAttributes rd)
    {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        Employee employee = adminService.editEmployee(empId);
        model.addAttribute("emp", employee);
        return "admin/Admin_editEmployee";
    }

    @PostMapping("editEmployee")
    public String editEmployeeFinal(HttpSession session,
                                    @ModelAttribute Employee modifiedEmployee,
                                    @RequestParam(value = "emp_pic", required = false) MultipartFile file,
                                    RedirectAttributes rda)
    {
        if (!isAdminLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.editEmployeeFinal(modifiedEmployee, file);
        rda.addFlashAttribute("msg", "Employee Updated Successfully");
        return "redirect:/admin/allEmployees";
    }

    // ========================================
    // BOOKING MANAGEMENT
    // ========================================

    // GET - View all bookings
    @GetMapping("view-bookings")
    public String viewBookings(HttpSession session, Model model, RedirectAttributes rd) {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<Booking> bookings = adminService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/Admin_ViewBooking";
    }

    // POST - Approve/Activate booking
    @PostMapping("booking/approve")
    public String approveBooking(HttpSession session,
                                 @RequestParam Integer id,
                                 @RequestParam String timeSlot,
                                 @RequestParam(required = false) String adminMessage,
                                 RedirectAttributes rda) {
        if (!isAdminLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.approveBooking(id, timeSlot, adminMessage);
        rda.addFlashAttribute("msg", "Booking activated successfully!");
        return "redirect:/admin/view-bookings";
    }

    // POST - Reject booking
    @PostMapping("booking/reject")
    public String rejectBooking(HttpSession session,
                                @RequestParam Integer id,
                                @RequestParam(required = false) String adminMessage,
                                RedirectAttributes rda) {
        if (!isAdminLoggedIn(session)) {
            rda.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        adminService.rejectBooking(id, adminMessage);
        rda.addFlashAttribute("msg", "Booking rejected.");
        return "redirect:/admin/view-bookings";
    }

    // ========================================
    // FEEDBACK MANAGEMENT
    // ========================================

    @GetMapping("view-feedback")
    public String viewFeedback(HttpSession session, Model model, RedirectAttributes rd) {
        if (!isAdminLoggedIn(session)) {
            rd.addFlashAttribute("msg", "Unauthorized Access, Please Login First");
            return "redirect:/admin/adminLogin";
        }

        List<Feedback> feedbacks = adminService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "admin/Admin_ViewFeedback";
    }
}
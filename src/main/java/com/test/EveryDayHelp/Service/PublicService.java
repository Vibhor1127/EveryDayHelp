package com.test.EveryDayHelp.Service;

import com.test.EveryDayHelp.Entity.AdminNotice;
import com.test.EveryDayHelp.Entity.Booking;
import com.test.EveryDayHelp.Entity.Contacts;
import com.test.EveryDayHelp.Entity.Feedback;
import com.test.EveryDayHelp.Entity.UserDetails;
import com.test.EveryDayHelp.Repository.BookingRepository;
import com.test.EveryDayHelp.Repository.ContactRepository;
import com.test.EveryDayHelp.Repository.FeedbackRepository;
import com.test.EveryDayHelp.Repository.NoticeRepository;
import com.test.EveryDayHelp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicService
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private final ContactRepository contactRepository;

    public PublicService(ContactRepository contactRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.contactRepository = contactRepository;
    }

    public void addContact(Contacts contacts) {
        contactRepository.save(contacts);
    }

    public void registerUser(UserDetails userDetails, MultipartFile file) {
        try
        {
            String projectRoot = System.getProperty("user.dir");
            System.out.println("Project Root is " + projectRoot);

            String foldername = "uploads";
            String uploadpath = projectRoot + File.separator + foldername;
            System.out.println("Uploaded Path is " + uploadpath);

            File uploadDir = new File(uploadpath);
            if(!uploadDir.exists())
            {
                uploadDir.mkdirs();
                System.out.println("Created upload folder at : " + uploadDir.getAbsolutePath());
            }

            String originalFilename = file.getOriginalFilename();
            System.out.println("File name is " + originalFilename);

            String uniquefileName = System.currentTimeMillis() + " __ " + originalFilename;
            File destinationFile = new File(uploadDir, uniquefileName);

            file.transferTo(destinationFile);
            System.out.println("Images saved to : " + destinationFile.getAbsolutePath());

            String DB_PATH = foldername +"/" + uniquefileName;
            userDetails.setPic(DB_PATH);

            String encryptedPassword = passwordEncoder.encode(userDetails.getPassword());
            System.out.println("Password After encryption " + encryptedPassword);

            userDetails.setPassword(encryptedPassword);
            userRepository.save(userDetails);
        }
        catch (IOException ie)
        {
            ie.printStackTrace();
        }
    }

    public UserDetails UserLogin(String email, String rawpassword) {
        String sql = "select * from user_details where email=? ";
        try {
            UserDetails userDetails = jdbcTemplate.queryForObject(sql, new RowMapper<UserDetails>() {
                @Override
                public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserDetails ud = new UserDetails();
                    ud.setEmail(rs.getString("email"));
                    ud.setAddress(rs.getString("address"));
                    ud.setPhone(rs.getString("phone"));
                    ud.setName(rs.getString("name"));
                    ud.setPic(rs.getString("pic"));
                    ud.setPassword(rs.getString("password"));
                    return ud;
                }
            }, email);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(rawpassword, userDetails.getPassword());
            if (passwordMatches) {
                userDetails.setPassword(null);
                return userDetails;
            } else {
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBooking(Booking booking) {
        booking.setStatus("Pending");
        booking.setDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(String email) {
        return bookingRepository.findByEmail(email);
    }

    public void addFeedback(Feedback feedback) {
        Feedback f = feedbackRepository.findByEmail(feedback.getEmail());
        if (f != null) {
            f.setName(feedback.getName());
            f.setRemarks(feedback.getRemarks());
            f.setRating(feedback.getRating());
            feedbackRepository.save(f);
        } else {
            feedbackRepository.save(feedback);
        }
    }

    public List<AdminNotice> getAllNotices() {
        return noticeRepository.findAll();
    }
}
package com.jcnetwork.members.controller.user;

import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.data.user.UserSettings;
import com.jcnetwork.members.model.data.user.resume.Resume;
import com.jcnetwork.members.model.data.user.resume.UniversityEducation;
import com.jcnetwork.members.model.ui.Toast;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private ControllerUtils utils;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ModelAndView getProfile() {

        User user = utils.getUserFromContext();

        Resume resume = new Resume();
        if(user.getResume() != null){
            resume = user.getResume();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userDetails", user.getUserDetails());
        modelAndView.addObject("resume", resume);
        modelAndView.addObject("newUniversityEducation", new UniversityEducation());
        modelAndView.addObject("userSettings", user.getUserSettings());
        modelAndView.addObject("navbarLinks", utils.navbarLinks(user));
        modelAndView.setViewName("sites/user/profilePage");
        return modelAndView;
    }

    @PostMapping("/saveUserSettings")
    public RedirectView saveUserSettings(@Valid UserSettings userSettings, RedirectAttributes redirectAttributes) {

        User user = utils.getUserFromContext();
        user.setUserSettings(userSettings);
        userService.saveUser(user);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Deine Einstellungen wurden aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/user/profile");
    }

    @PostMapping("/saveUserDetails")
    public RedirectView saveUserDetails(@Valid UserDetails userDetails,  RedirectAttributes redirectAttributes) {

        User user = utils.getUserFromContext();
        user.setUserDetails(userDetails);
        userService.saveUser(user);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Deine Daten wurden aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/user/profile");
    }

    @PostMapping("/updateProfilePicture")
    public RedirectView updateProfilePicture(
            @Valid UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        User user = utils.getUserFromContext();
        user.getUserDetails().setProfilePictureBase64(userDetails.getProfilePictureBase64());
        userService.saveUser(user);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Deine Profilbild wurde aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/user/profile");
    }

    @PostMapping("/saveResume")
    public RedirectView saveResume(@Valid Resume resume,  RedirectAttributes redirectAttributes) {

        User user = utils.getUserFromContext();
        user.setResume(resume);
        userService.saveUser(user);

        Toast toast = new Toast(
                "Speichern Erfolgreich",
                "Deine Lebenslauf wurde aktualisiert.",
                "success"
        );
        redirectAttributes.addFlashAttribute("toast", toast);

        return new RedirectView("/user/profile");
    }
}

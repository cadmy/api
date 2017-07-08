package ru.cadmy.api.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import ru.cadmy.api.model.Profile;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by ssemenov on 28.06.17.
 */
@Service
public class ProfileService {

    List<Profile> profiles = Lists.newArrayList(new Profile(1, "Petr I", "1672"),
                                                new Profile(2, "Ekaterina II", "1729"),
                                                new Profile(3,"Alexander III", "1845"));

    public Profile getProfileById(int id) {
        Profile result;
        try {
            result = profiles.stream().filter(profile -> profile.getId() == id)
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Multiple elements with same id");
                    })
                    .get();
        } catch (IllegalStateException e) {
            result = new Profile(-1, "Multiple elements with same id", "");
        } catch (NoSuchElementException e) {
            result = new Profile(-1, "No such element", "");
        }
        return result;
    }

}

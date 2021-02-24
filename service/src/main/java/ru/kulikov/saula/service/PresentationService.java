package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.repository.PresentationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PresentationService {

    @Autowired
    private PresentationRepository presentationRepository;

    public List<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public Presentation findById(int theId) {
        Optional<Presentation> result = presentationRepository.findById(theId);

        if (result.isPresent()) {
            return result.get();
        }
        throw new ResourceNotFoundException("Did not find presentation id - " + theId);
    }

    public void save(Presentation thePresentation) {
        presentationRepository.save(thePresentation);
    }

    public void deleteById(int theId) {
        presentationRepository.deleteById(theId);
    }
}

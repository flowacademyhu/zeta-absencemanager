package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;
}

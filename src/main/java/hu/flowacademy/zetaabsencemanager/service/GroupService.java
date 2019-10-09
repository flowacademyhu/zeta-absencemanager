package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
}

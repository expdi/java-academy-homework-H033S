package com.expeditors.adoptionservice.services.adopter;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.factory.TestFactory;
import com.expeditors.adoptionservice.service.implementation.AdopterServiceImpl;
import com.expeditors.adoptionservice.services.BaseServiceUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
public class AdopterServiceUnitTest
        extends BaseServiceUnitTest<Adopter> {

    @Mock
    private BaseDao<Adopter> adopterDAO;
    @InjectMocks
    private AdopterServiceImpl adopterService;


    @Override
    @BeforeEach
    public void init() {
        baseDAO = adopterDAO;
        baseService = adopterService;
        mockEntity = TestFactory.getAdopterInstance();
    }
}

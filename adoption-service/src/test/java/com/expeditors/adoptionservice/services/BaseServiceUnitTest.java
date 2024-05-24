package com.expeditors.adoptionservice.services;

import com.expeditors.adoptionservice.dao.BaseDao;
import com.expeditors.adoptionservice.domain.AbstractEntity;
import com.expeditors.adoptionservice.service.BaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

public abstract class BaseServiceUnitTest<TEntity extends AbstractEntity> {

    protected BaseDao<TEntity> baseDAO;
    protected BaseService<TEntity> baseService;
    protected TEntity mockEntity;


    public abstract void init();

    @Test
    public void addEntity_ThrowsIllegalArgumentException_WhenAdoptionPassedIsNull(){

        assertThrows(IllegalArgumentException.class,
                () -> baseService.addEntity(null));
    }

    @Test
    public void addEntity_RunSuccessfully_WithNotNullValue(){

        doReturn(mockEntity)
                .when(baseDAO).insert(any());

        var adoption = baseService.addEntity(mockEntity);

        assertFalse(Objects.isNull(adoption));
        assertSame(mockEntity, adoption);
    }



    @Test
    public void getEntityById_ReturnNull_WhenIdIsNotFound(){
        Mockito.doReturn(null)
                .when(baseDAO).findById(anyInt());

        assertNull(baseService.getEntityById(0));
    }

    @Test
    public void getEntityById_ReturnEntity_WhenIdIsFound(){

        doReturn(mockEntity)
                .when(baseDAO).findById(1);

        var objectFound = baseService.getEntityById(1);

        assertSame(mockEntity, objectFound);
    }



    @Test
    public void updateEntity_ThrowsIllegalArgumentException_WhenEntityPassedIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> baseService.updateEntity(null));
    }

    @Test
    public void updateEntity_ReturnTrue_WhenIdIsFoundInRepo(){

        doReturn(true)
                .when(baseDAO).update(mockEntity);

        assertTrue(baseService.updateEntity(mockEntity));
    }

    @Test
    public void updateEntity_ReturnFalse_WhenIdIsNotFoundInRepo(){

        doReturn(false)
                .when(baseDAO).update(mockEntity);

        assertFalse(baseService.updateEntity(mockEntity));
    }


    @Test
    public void deleteEntity_ReturnsFalse_WhenIdIsNotFound(){
        doReturn(false)
                .when(baseDAO).delete(anyInt());

        assertFalse(baseService.deleteEntity(0));
    }

    @Test
    public void deleteEntity_ReturnsTrue_WhenIdIsFound(){
        doReturn(true)
                .when(baseDAO).delete(anyInt());

        assertTrue(baseService.deleteEntity(1));
    }

    @Test
    public void findAllAdoptions_RunSuccessful(){
        var mockAdoptionList = List.of(
                mockEntity,
                mockEntity
        );

        doReturn(mockAdoptionList)
                .when(baseDAO).findAll();

        var adoptionListReturned = baseService.getAllEntities();

        assertEquals(mockAdoptionList.size(), adoptionListReturned.size());
        assertSame(mockAdoptionList, adoptionListReturned);
    }
}


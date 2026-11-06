package com.mns.cda.suivimns.unit.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.dto.flat.PostCommentDto;
import com.mns.cda.suivimns.exception.TrialAccountRestrictedException;
import com.mns.cda.suivimns.mapper.entity.CommentMapper;
import com.mns.cda.suivimns.mapper.entity.KnowledgeMapper;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.entity.CommentService;
import com.mns.cda.suivimns.service.entity.KnowledgeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Trial accounts (rank 0 technicians) can't alter shared demo data: no comments,
 * and only their own knowledge articles can be edited.
 */
@ExtendWith(MockitoExtension.class)
class TrialAccountRestrictionsUnitTest {

    @Mock private CommentDao commentDao;
    @Mock private CommentMapper commentMapper;
    @Mock private TicketDao ticketDao;
    @Mock private AppUserDao appUserDao;
    @Mock private ActivityService activityService;
    @Mock private KnowledgeDao knowledgeDao;
    @Mock private KnowledgeMapper knowledgeMapper;

    @Test
    void onlyRankZeroTechniciansAreTrialAccounts() {
        assertTrue(new AppUserDetails(technician(1, (byte) 0)).isTrialTechnician());
        assertFalse(new AppUserDetails(technician(1, (byte) 1)).isTrialTechnician());
    }

    @Test
    void trialAccountCannotComment() {
        CommentService commentService = new CommentService(commentDao, commentMapper, ticketDao, appUserDao, activityService);
        AppUserDetails trial = new AppUserDetails(technician(1, (byte) 0));

        assertThrows(TrialAccountRestrictedException.class,
                () -> commentService.save(new PostCommentDto("hello", 3), trial));

        verifyNoInteractions(commentDao, activityService);
    }

    @Test
    void trialAccountCannotEditSomeoneElsesArticle() {
        KnowledgeService knowledgeService = new KnowledgeService(knowledgeDao, knowledgeMapper, appUserDao);
        when(knowledgeDao.findById(10)).thenReturn(Optional.of(articleBy(technician(2, (byte) 1))));

        AppUserDetails trial = new AppUserDetails(technician(1, (byte) 0));
        assertThrows(TrialAccountRestrictedException.class,
                () -> knowledgeService.update(10, mock(KnowledgeDto.class), trial));

        verify(knowledgeDao, never()).save(any());
    }

    @Test
    void trialAccountCanEditItsOwnArticle() {
        KnowledgeService knowledgeService = new KnowledgeService(knowledgeDao, knowledgeMapper, appUserDao);
        Technician trialTechnician = technician(1, (byte) 0);
        Knowledge article = articleBy(trialTechnician);
        when(knowledgeDao.findById(10)).thenReturn(Optional.of(article));
        when(knowledgeDao.save(article)).thenReturn(article);

        knowledgeService.update(10, mock(KnowledgeDto.class), new AppUserDetails(trialTechnician));

        verify(knowledgeDao).save(article);
    }

    @Test
    void regularTechnicianCanEditAnyArticle() {
        KnowledgeService knowledgeService = new KnowledgeService(knowledgeDao, knowledgeMapper, appUserDao);
        Knowledge article = articleBy(technician(2, (byte) 1));
        when(knowledgeDao.findById(10)).thenReturn(Optional.of(article));
        when(knowledgeDao.save(article)).thenReturn(article);

        knowledgeService.update(10, mock(KnowledgeDto.class), new AppUserDetails(technician(1, (byte) 1)));

        verify(knowledgeDao).save(article);
    }

    private static Knowledge articleBy(Technician author) {
        Knowledge knowledge = new Knowledge();
        knowledge.setIdKnowledge(10);
        knowledge.setAuthor(author);
        return knowledge;
    }

    private static Technician technician(int id, byte rank) {
        Technician technician = new Technician();
        technician.setIdAppUser(id);
        technician.setFirstName("First");
        technician.setLastName("Last");
        technician.setRank(rank);
        return technician;
    }
}

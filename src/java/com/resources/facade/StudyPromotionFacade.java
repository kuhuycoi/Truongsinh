package com.resources.facade;

import com.resources.entity.Module;
import com.resources.entity.StudyPromotion;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class StudyPromotionFacade extends AbstractFacade {

    public StudyPromotionFacade() {
        super(StudyPromotion.class);
    }

    public void pageData(HistoryPagination historyPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(StudyPromotion.class, "s");
                cr.createAlias("s.customerStart", "cStart");
                cr.createAlias("s.customerEnd", "cEnd");
                List<String> listKeywords = historyPagination.getKeywords();
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(historyPagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + historyPagination.getSearchString() + "%'"));
                }
                cr.add(disj);

                cr.setProjection(Projections.rowCount());
                historyPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("cStart.userName"), "customerStart")
                        .add(Projections.property("cEnd.userName"), "customerEnd")
                        .add(Projections.property("totalMember"), "totalMember")
                        .add(Projections.property("moneyperone"), "moneyperone")
                        .add(Projections.property("moneypercircle"), "moneypercircle")
                        .add(Projections.property("isActive"), "isActive"))
                        .setResultTransformer(Transformers.aliasToBean(com.resources.bean.StudyPromotion.class));
                cr.setFirstResult(historyPagination.getFirstResult());
                cr.setMaxResults(historyPagination.getDisplayPerPage());
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));

                historyPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public int insertMemberPromotion(int cusStart, int cusEnd, String name, BigDecimal moneypercircle, BigDecimal totalMoney) throws Exception {
        Session session = null;
        Transaction trans = null;
        int rs = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Query q = session.createSQLQuery("insertMemberPromotion :cusStart,:cusEnd,:name,:totalMoney,:moneypercircle");
                q.setParameter("cusStart", cusStart);
                q.setParameter("cusEnd", cusEnd);
                q.setParameter("name", name);
                q.setParameter("moneypercircle", moneypercircle);
                q.setParameter("totalMoney", totalMoney);
                rs = (Integer) q.uniqueResult();
                trans.commit();
            }
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return rs;
    }
    public int activeStudyPromotion(Integer id) throws Exception {
        Session session = null;
        Transaction trans = null;
        int rs = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Query q = session.createSQLQuery("Update StudyPromotion set isActive=case id "
                        + "when :id then 1 "
                        + "else 0 "
                        + "end");
                q.setParameter("id", id);
                rs=q.executeUpdate();
                trans.commit();
            }
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return rs;
    }

    @Override
    public StudyPromotion find(int id) {
        Session session = null;
        StudyPromotion obj = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            obj = (StudyPromotion) session.get(StudyPromotion.class, id);
            Hibernate.initialize(obj.getCustomerStart());
            Hibernate.initialize(obj.getCustomerEnd());
        } catch (Exception e) {
            Logger.getLogger(StudyPromotion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return obj;
    }
    
    

    @Override
    public void pageData(DefaultAdminPagination pagination) {
    }
}

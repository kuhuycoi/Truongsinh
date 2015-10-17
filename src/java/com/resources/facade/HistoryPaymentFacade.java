package com.resources.facade;

import com.resources.bean.HistoryPaymentBean;
import com.resources.entity.HistoryPayment;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

public class HistoryPaymentFacade extends AbstractFacade {

    public HistoryPaymentFacade() {
        super(HistoryPayment.class);
    }

    public void pageDataPayment(ReportPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryPayment.class, "h");
                cr.createAlias("h.customerID", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("h.admcheck", "a", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("h.provincialAgenciesID", "p", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.and(Restrictions.eq("h.isDelete", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));

                if (pagination.getDay() != -1 && pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    if (pagination.getDay() == 1) {
                        cr.add(Restrictions.sqlRestriction("DAY(datetimeCreated)<=15"));
                    } else if (pagination.getDay() == 2) {
                        cr.add(Restrictions.sqlRestriction("DAY(datetimeCreated)>15"));
                    }
                }

                if (pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(datetimeCreated)=" + pagination.getMonth()));
                }

                if (pagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(datetimeCreated)=" + pagination.getYear()));
                }

                List<String> listKeywords = pagination.getKeywords();
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(pagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + pagination.getSearchString() + "%'"));
                }
                cr.add(disj);
                cr.setProjection(Projections.rowCount());
                pagination.setTotalResult(((Long) cr.uniqueResult()).intValue());

                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("datetimeCreated"), "datetimeCreated")
                        .add(Projections.property("orderforDate"), "orderforDate")
                        .add(Projections.property("totalMoney"), "totalMoney")
                        .add(Projections.property("percentPay"), "percentPay")
                        .add(Projections.property("totalPay"), "totalPay")
                        .add(Projections.property("isPay"), "isPay")
                        .add(Projections.property("isBank"), "isBank")
                        .add(Projections.property("bank"), "bank")
                        .add(Projections.property("descrip"), "descrip")
                        .add(Projections.property("codeBank"), "codeBank")
                        .add(Projections.property("chuTK"), "chuTK")
                        .add(Projections.property("a.userName"), "adminUsername")
                        .add(Projections.property("p.name"), "provincialAgenciesName")
                        .add(Projections.property("cus.userName"), "customerUsername")
                        .add(Projections.property("cus.firstName"), "customerFirstName")
                        .add(Projections.property("cus.lastName"), "customerLastName"));
                cr.setResultTransformer(Transformers.aliasToBean(HistoryPaymentBean.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));

                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(HistoryPayment.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public Integer insert(Map map) throws Exception {
        Session session = null;
        Integer rs = 0;
        Transaction trans = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Boolean isBank = (Boolean) map.get("isBank");
                Integer cusID = (Integer) map.get("cusId");
                String Bank = (String) map.get("bank");
                String codebank = (String) map.get("codeBank");
                String chutk = (String) map.get("chuTK");
                Integer provinID = Integer.valueOf((String) map.get("provincialAgenciesID"));
                String descrip = (String) map.get("descrip");
                Date datetime = new SimpleDateFormat("dd/MM/yyyy").parse((String) map.get("date"));
                Query q = session.createSQLQuery("Payment :cusID,:datetime,:descrip,:codebank,:chutk,:provinID,:isBank,:Bank");
                q.setParameter("cusID", cusID)
                        .setParameter("datetime", datetime)
                        .setParameter("descrip", descrip)
                        .setParameter("codebank", codebank)
                        .setParameter("chutk", chutk)
                        .setParameter("provinID", provinID)
                        .setParameter("isBank", isBank)
                        .setParameter("Bank", Bank);
                rs = (Integer) q.uniqueResult();
                trans.commit();
            }
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            Logger.getLogger(HistoryPayment.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return rs;
    }

    public Integer edit(Map map) throws Exception {
        Session session = null;
        Integer rs = 0;
        Transaction trans = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Integer day = Integer.parseInt((String)map.get("day"));
                Integer month = Integer.parseInt((String)map.get("month"));
                Integer year = Integer.parseInt((String)map.get("year"));
                Integer percent = Integer.parseInt((String)map.get("percent"));
                Query q = session.createSQLQuery("UpdatePayment :day,:month,:year,:percent");
                q.setParameter("day", day)
                        .setParameter("month", month)
                        .setParameter("year", year)
                        .setParameter("percent", percent);
                rs = (Integer) q.uniqueResult();
                trans.commit();
            }
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            Logger.getLogger(HistoryPayment.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return rs;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

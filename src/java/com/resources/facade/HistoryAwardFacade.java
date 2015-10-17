package com.resources.facade;

import com.resources.bean.ExcelFile;
import com.resources.bean.HistoryAward;
import com.resources.entity.HistoryAwards;
import com.resources.entity.Module;
import com.resources.entity.Triandot2;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.ArrayUtils;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

public class HistoryAwardFacade extends AbstractFacade {

    public HistoryAwardFacade() {
        super(HistoryAwards.class);
    }

    public void pageData(HistoryPagination historyPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "a");
                cr.createAlias("a.customer", "cus");

                cr.add(Restrictions.and(Restrictions.eq("a.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));
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
                        .add(Projections.property("cus.userName"), "userName")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("dateCreated"), "dateCreated"))
                        .setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
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

    public void pageData(ReportPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "h");
                cr.createAlias("h.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.and(Restrictions.eq("h.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));

                if (pagination.getDay() != -1 && pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    if (pagination.getDay() == 1) {
                        cr.add(Restrictions.sqlRestriction("DAY(DateCreated)<=15"));
                    } else if (pagination.getDay() == 2) {
                        cr.add(Restrictions.sqlRestriction("DAY(DateCreated)>15"));
                    }
                }

                if (pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(DateCreated)=" + pagination.getMonth()));
                }

                if (pagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(DateCreated)=" + pagination.getYear()));
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

                String queryString = "select count(*) from (select h.customerId from HistoryAwards h join Customer c on h.CustomerId=c.id where h.isDeleted=0";
                if (pagination.getDay() != -1 && pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    if (pagination.getDay() == 1) {
                        queryString += " and DAY(DateCreated)<=15";
                    } else if (pagination.getDay() == 2) {
                        queryString += " and DAY(DateCreated)>15";
                    }
                }
                if (pagination.getMonth() != -1 && pagination.getYear() != -1) {
                    queryString += " and MONTH(h.dateCreated)=" + pagination.getMonth();
                }

                if (pagination.getYear() != -1) {
                    queryString += " and YEAR(h.dateCreated)=" + pagination.getYear();
                }
                queryString += " and c.IsDelete=0 and c.IsActive=1 group by h.customerId)z";
                Query q = session.createSQLQuery(queryString);

                pagination.setTotalResult((Integer) q.uniqueResult());

                cr.setProjection(Projections.projectionList()
                        .add(Projections.sum("pricePv"), "pricePv")
                        .add(Projections.groupProperty("cus.id"), "cusId")
                        .add(Projections.groupProperty("cus.userName"), "userName")
                        .add(Projections.groupProperty("cus.firstName"), "firstName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName"));
                cr.setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));

                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void setExportComissionDistributorFile(ExcelFile file, Integer month, Integer year) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select ha.customerId,c.firstName,c.lastName,c.userName,c.peoplesIdentity,c.email,c.mobile,c.address,c.taxCode,"
                        + "c.billingAddress,isnull(t1.total,0) total1, "
                        + "isnull(t2.total,0) total2,isnull(t3.total,0) total3, "
                        + "isnull(t4.total,0) total4,isnull(t5.total,0) total5,isnull(t6.total,0) total6,isnull(t7.total,0) total7, isnull(sum(ha.PricePv),0) total8 "
                        + "from HistoryAwards ha left join Customer c "
                        + "on ha.CustomerId=c.id "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=1 group by CustomerId) t1 "
                        + "on ha.CustomerId=t1.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=4 group by CustomerId) t2 "
                        + "on ha.CustomerId=t2.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=13 group by CustomerId) t3 "
                        + "on ha.CustomerId=t3.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=2 group by CustomerId) t4 "
                        + "on ha.CustomerId=t4.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=3 group by CustomerId) t5 "
                        + "on ha.CustomerId=t5.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=6 group by CustomerId) t6 "
                        + "on ha.CustomerId=t6.CustomerId "
                        + "left join (select CustomerId,sum(PricePv) as total from HistoryAwards where Month(DateCreated)= :month and Year(DateCreated)= :year and IsDeleted=0 and CheckAwardkId=14 group by CustomerId) t7 "
                        + "on ha.CustomerId=t7.CustomerId "
                        + "where ha.IsDeleted=0 and c.IsDelete=0 and c.IsActive=1 and Month(ha.DateCreated)= :month and Year(ha.DateCreated)= :year "
                        + "group by ha.CustomerId,c.firstName,c.lastName,c.Username,c.peoplesIdentity,c.Email,c.mobile,c.Address,c.TaxCode,c.BillingAddress,t1.total,t2.total,t3.total,t4.total,t5.total,t6.total,t7.total")
                        .addScalar("customerId", IntegerType.INSTANCE)
                        .addScalar("firstName", StringType.INSTANCE)
                        .addScalar("lastName", StringType.INSTANCE)
                        .addScalar("userName", StringType.INSTANCE)
                        .addScalar("peoplesIdentity", StringType.INSTANCE)
                        .addScalar("email", StringType.INSTANCE)
                        .addScalar("mobile", StringType.INSTANCE)
                        .addScalar("address", StringType.INSTANCE)
                        .addScalar("taxCode", StringType.INSTANCE)
                        .addScalar("billingAddress", StringType.INSTANCE)
                        .addScalar("total1", BigDecimalType.INSTANCE)
                        .addScalar("total2", BigDecimalType.INSTANCE)
                        .addScalar("total3", BigDecimalType.INSTANCE)
                        .addScalar("total4", BigDecimalType.INSTANCE)
                        .addScalar("total5", BigDecimalType.INSTANCE)
                        .addScalar("total6", BigDecimalType.INSTANCE)
                        .addScalar("total7", BigDecimalType.INSTANCE)
                        .addScalar("total8", BigDecimalType.INSTANCE);
                q.setParameter("month", month);
                q.setParameter("year", year);
                List<String> header = new ArrayList();
                header.add("ID");
                header.add("Họ");
                header.add("Tên");
                header.add("Tên đăng nhập");
                header.add("CMND");
                header.add("Email");
                header.add("SĐT");
                header.add("Địa chỉ liên hệ");
                header.add("Số TK ngân hàng");
                header.add("Địa chỉ ngân hàng");
                header.add("Hoa hồng hoàn vốn siêu tốc");
                header.add("Hoa hồng trực tiếp");
                header.add("Hoa hồng gián tiếp cặp");
                header.add("Hoa hồng tập huấn");
                header.add("Hoa hồng tăng trưởng");
                header.add("Lương cứng chuyên viên dự án");
                header.add("Quỹ khuyến học");
                header.add("Tổng thưởng");
                file.setTitles(header);
                List rs = q.list();
                for (Object rows : rs) {
                    Object[] row = (Object[]) rows;
                    row[10] = CustomFunction.formatCurrency((BigDecimal) row[10]);
                    row[11] = CustomFunction.formatCurrency((BigDecimal) row[11]);
                    row[12] = CustomFunction.formatCurrency((BigDecimal) row[12]);
                    row[13] = CustomFunction.formatCurrency((BigDecimal) row[13]);
                    row[14] = CustomFunction.formatCurrency((BigDecimal) row[14]);
                    row[15] = CustomFunction.formatCurrency((BigDecimal) row[15]);
                    row[16] = CustomFunction.formatCurrency((BigDecimal) row[16]);
                    row[17] = CustomFunction.formatCurrency((BigDecimal) row[17]);
                }
                file.setContents(rs);
                file.setFileName("Thong ke hoa hong NPP " + month + "-" + year);
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void setExportTrianFile(ExcelFile file) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select t.trueVT,c.firstName,c.lastName,c.title,c.mobile,c.email,c.peoplesIdentity,"
                        + "c.billingAddress,c.taxCode,t.datetimecreated,t.dem,t.levelup,null as total from Triandot2 t "
                        + "left join Customer c on t.customerID=c.id where c.isDelete=0 and c.isActive=1 order by t.trueVT asc")
                        .addScalar("trueVT", IntegerType.INSTANCE)
                        .addScalar("firstName", StringType.INSTANCE)
                        .addScalar("lastName", StringType.INSTANCE)
                        .addScalar("title", StringType.INSTANCE)
                        .addScalar("mobile", StringType.INSTANCE)
                        .addScalar("email", StringType.INSTANCE)
                        .addScalar("peoplesIdentity", StringType.INSTANCE)
                        .addScalar("billingAddress", StringType.INSTANCE)
                        .addScalar("taxCode", StringType.INSTANCE)
                        .addScalar("datetimecreated", TimestampType.INSTANCE)
                        .addScalar("dem", IntegerType.INSTANCE)
                        .addScalar("levelup", IntegerType.INSTANCE)
                        .addScalar("total", BigDecimalType.INSTANCE);
                List<String> header = new ArrayList();
                header.add("ID");
                header.add("Họ");
                header.add("Tên");
                header.add("Bí danh");
                header.add("SĐT");
                header.add("Email");
                header.add("CMND");
                header.add("Địa chỉ ngân hàng");
                header.add("Số tài khoản");
                header.add("Thời gian tri ân");
                header.add("Tích lũy");
                header.add("Mức hoàn phí");
                header.add("Thành tiền");
                file.setTitles(header);
                List rs = q.list();
                Integer money = 0;
                for (Object rows : rs) {
                    Object[] row = (Object[]) rows;
                    row[9] = CustomFunction.formatTime((Date) row[9]);
                    switch ((Integer) row[11]) {
                        case 1: {
                            money = 20;
                            break;
                        }
                        case 2: {
                            money = 120;
                            break;
                        }
                        case 3: {
                            money = 320;
                            break;
                        }
                        case 4: {
                            money = 1000;
                            break;
                        }
                    }
                    row[12] = CustomFunction.formatCurrency(BigDecimal.valueOf(money));
                }
                file.setContents(rs);
                file.setFileName("Thong ke tri an");
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public int calcSalary() throws Exception {
        Session session = null;
        int result = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                result = (Integer) session.createSQLQuery("CalcSalary").uniqueResult();
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public List<HistoryAward> pageData(HistoryPagination pagination, int cusId, int checkAwardId, int month, int year) throws Exception {
        Session session = null;
        List<HistoryAward> list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "ha");
                cr.createAlias("ha.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("ha.checkAwards", "a", JoinType.LEFT_OUTER_JOIN);
                cr.add(Restrictions.eq("isDeleted", false))
                        .add(Restrictions.eq("cus.isDelete", false))
                        .add(Restrictions.eq("cus.isActive", true))
                        .add(Restrictions.eq("a.id", checkAwardId));
                if (month != -1 && year != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(dateCreated)=" + month));
                }

                if (year != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(dateCreated)=" + year));
                }
                cr.setProjection(Projections.rowCount());
                pagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"))
                        .add(Projections.property("name"))
                        .add(Projections.property("price"))
                        .add(Projections.property("pricePv"))
                        .add(Projections.property("dateCreated")));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
                return cr.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public LinkedHashMap reportAllAwardByMonth(int year) {
        Session session = null;
        LinkedHashMap<String, Object[]> report = new LinkedHashMap();
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH);
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllAwardByMonth :year").setParameter("year", year);
                String key;
                for (Object rows : q.list()) {
                    Object[] row = (Object[]) rows;
                    for (int i = currentMonth + 2; i <= 12; i++) {
                        row[i] = null;
                    }
                    switch ((Integer) row[0]) {
                        case 1: {
                            key = "Hoa hồng hoàn vốn siêu tốc";
                            break;
                        }
                        case 2: {
                            key = "Hoa hồng tập huấn";
                            break;
                        }
                        case 3: {
                            key = "Hoa hồng tăng trưởng";
                            break;
                        }
                        case 4: {
                            key = "Hoa hồng trực tiếp";
                            break;
                        }
                        case 6: {
                            key = "Lương cứng chuyên viên dự án";
                            break;
                        }
                        case 13: {
                            key = "Hoa hồng gián tiếp cặp";
                            break;
                        }
                        case 14: {
                            key = "Quỹ khuyến học";
                            break;
                        }
                        default: {
                            key = "Thưởng lên cấp";
                            break;
                        }
                    }
                    report.put(key, ArrayUtils.removeItem(row, 0));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return report;
    }

    public Map reportAllComissionByMonth(int year) {
        Session session = null;
        LinkedHashMap<String, Object[]> report = new LinkedHashMap();
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH);
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllComissionByMonth :year").setParameter("year", year);
                for (Object rows : q.list()) {
                    Object[] row = (Object[]) rows;
                    for (int i = currentMonth + 2; i <= 12; i++) {
                        row[i] = null;
                    }
                    String key;
                    switch ((Integer) row[0]) {
                        case 0: {
                            key = "Tổng Thu";
                            break;
                        }
                        case 1: {
                            key = "Tổng Chi";
                            break;
                        }
                        case 2: {
                            key = "Lãi Xuất";
                            break;
                        }
                        case 3: {
                            key = "Chi/Thu(%)";
                            break;
                        }
                        default: {
                            key = "Error";
                            break;
                        }
                    }
                    report.put(key, ArrayUtils.removeItem(row, 0));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return report;
    }

    public Integer countNewUserInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return countNewUser(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
    }

    public Integer countNewUserInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return countNewUser(null, c.get(Calendar.YEAR));
    }

    public Integer countNewUser(Integer month, Integer year) {
        Session session = null;
        Integer result = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("CountNewUser :month,:year")
                    .setParameter("month", month)
                    .setParameter("year", year);
            result = (Integer) q.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getTotalInInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return getTotalIn(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalInInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return getTotalIn(null, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalIn(Integer month, Integer year, Integer cusid) {
        Session session = null;
        BigDecimal result = BigDecimal.valueOf(0);
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("GetTotalIn :month,:year,:cusid")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .setParameter("cusid", cusid);
            result = (BigDecimal) q.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List reportAllTotalAwardInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return reportAllTotalAwardByMonth(null, c.get(Calendar.YEAR));
    }

    public List reportAllTotalAwardByMonth(Integer month, Integer year) {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("ReportAllTotalAwardByMonth :month,:year")
                    .addScalar("Name", StringType.INSTANCE)
                    .addScalar("Total", BigDecimalType.INSTANCE)
                    .setParameter("month", month)
                    .setParameter("year", year);
            result = q.list();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getTotalOutInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return getTotalOut(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalOutInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return getTotalOut(null, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalOut(Integer month, Integer year, Integer cusid) {
        Session session = null;
        BigDecimal result = BigDecimal.valueOf(0);
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("GetTotalOut :month,:year,:cusid")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .setParameter("cusid", cusid);
            result = (BigDecimal) q.uniqueResult();
        } catch (Exception e) {
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public void pageData(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "a");
                cr.createAlias("a.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("a.checkAwards", "ca", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.id", cusId));

                cr.add(Restrictions.eq("a.isDeleted", false));
                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));
                if (reportPagination.getMonth() != -1 && reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(DateCreated)=" + reportPagination.getMonth()));
                }

                if (reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(DateCreated)=" + reportPagination.getYear()));
                }
                
                if (reportPagination.getAwardType() != null) {
                    cr.add(Restrictions.eq("ca.id", reportPagination.getAwardType()));
                }
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("dateCreated"), "dateCreated"))
                        .setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                reportPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataTrian(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));

                if (reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(datetimecreated)=" + reportPagination.getYear()));
                }

                cr.add(Restrictions.eq("cus.id", cusId));

                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                reportPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataTrian(ReportPagination reportPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));
                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                cr.setFirstResult(reportPagination.getFirstResult());
                cr.setMaxResults(reportPagination.getDisplayPerPage());

                reportPagination.setDisplayList(cr.list());
                cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));

                cr.setProjection(Projections.rowCount());
                reportPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataByMonth(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllTotalAwardByMonthForCustomer :cusid,:awardType,:year").addScalar("Month", IntegerType.INSTANCE).addScalar("Total", BigDecimalType.INSTANCE);
                q.setParameter("cusid", cusId);
                q.setParameter("awardType", reportPagination.getAwardType());
                q.setParameter("year", reportPagination.getYear());

                reportPagination.setDisplayList(q.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataGByMonth(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("ReportTotalGByYear :cusid,:year,:g").addScalar("Month", IntegerType.INSTANCE).addScalar("VT", BigDecimalType.INSTANCE).addScalar("VP", BigDecimalType.INSTANCE);
                q.setParameter("cusid", cusId);
                q.setParameter("year", reportPagination.getYear());
                q.setParameter("g", reportPagination.getAwardType());

                reportPagination.setDisplayList(q.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public List getTop5AwardInMonth() {
        Calendar c = Calendar.getInstance();
        return getTop5Award(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
    }

    public List getTop5AwardInYear() {
        Calendar c = Calendar.getInstance();
        return getTop5Award(null, c.get(Calendar.YEAR));
    }

    public List getTop5Award(Integer month, Integer year) {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("GetTop5Award :month,:year")
                        .addScalar("Name", StringType.INSTANCE)
                        .addScalar("UserName", StringType.INSTANCE)
                        .addScalar("PricePv", BigDecimalType.INSTANCE);
                q.setParameter("month", month);
                q.setParameter("year", year);
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List findAllHistoryAwardYear() {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select YEAR(dateCreated) from HistoryAwards group by YEAR(dateCreated)");
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List findAllHistoryPaymentYear() {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select YEAR(datetimeCreated) from HistoryPayment group by YEAR(datetimeCreated)");
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
    }
}

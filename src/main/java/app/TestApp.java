package app;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import core.util.HibernateUtil;
import org.hibernate.Transaction;
import web.member.pojo.Member;

public class TestApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
//        String hql = "FROM Member WHERE username='admin'"; // find all
        String hql = "SELECT new web.member.pojo.Member(username, nickname, roleId)"
            + "FROM Member ";
//            + "WHERE username='admin'";
        List<Member> memberList = session.createQuery(hql, Member.class).list();
        for (Member member : memberList) {
            System.out.println(member);
        }

//        // insert
//        Member mem1 = new Member();
//        mem1.setUsername("Me");
//        mem1.setPassword("pwd");
//        mem1.setNickname("Mario");
//
//        Integer memID = new TestApp().insert(mem1);
//        System.out.println(memID);

        // delete by ID
//        System.out.println(new TestApp().deleteByID(5));
//
//        // update by ID
//        Member mem2 = new Member();
//        mem2.setId(1);
//        mem2.setUsername("WilliamS");
//        mem2.setPassword("pwd"); // origin: 123456
//        System.out.println(new TestApp().updateByID(mem2));

        // select by id
//        System.out.println(new TestApp().findByID(1).getNickname());
        ;
    }

    public Member findByID(Integer id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Member member = session.get(Member.class, id);
            return member;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateByID(Member newMember) {
        // we can only delete by ID so far
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            // 1. get persistence object
            Member member = session.load(Member.class, newMember.getId());

            // 2. object setter from new one to old one
            final String pwd = newMember.getPassword();
            if (pwd != null && !pwd.isEmpty()) {
                member.setPassword(newMember.getPassword());
            }

            final String nickname = newMember.getNickname();
            if (nickname != null && !nickname.isEmpty()) {
                member.setNickname(newMember.getNickname());
            }

            // 3. commit
            transaction.commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteByID(Integer id) {
        // we can only delete by ID so far
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Member member = session.load(Member.class, id);
            session.remove(member);
            transaction.commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Integer insert(Member member) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(member);
            transaction.commit();
            return member.getId();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void HelloWorld() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Member member = session.get(Member.class, 1);
        System.out.println("get username: " + member.getUsername());
        HibernateUtil.shutdown();
    }
}

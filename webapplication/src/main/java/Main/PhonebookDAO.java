package Main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PhonebookDAO {
    // DB 연결 정보
    private Connection conn;
    
    public PhonebookDAO() {
        try {
            // Oracle DB 드라이버 로드 및 연결
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
            System.out.println("DB 연결 성공: " + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // 1. 전화번호부 입력 기능 (insert)
    public boolean insert(PhonebookVO pb) {
        // 시퀀스를 사용하여 ID 값을 자동으로 할당
        String sql = "insert insto phonebook (id, name, hp, memo) VALUES (phonebook_id_seq.NEXTVAL, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getName());
            ps.setString(2, pb.getHp());
            ps.setString(3, pb.getMemo());
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. 전체 출력 기능 (selectAll)
    public List<PhonebookVO> selectAll() {
        List<PhonebookVO> pList = new ArrayList<>();
        String sql = "SELECT * FROM phonebook";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String hp = rs.getString("hp");
                String memo = rs.getString("memo");
                
                PhonebookVO pb = new PhonebookVO(id, name, hp, memo);
                pList.add(pb);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pList;
    }

    // 3. 검색 출력 기능 (search)
    public List<PhonebookVO> search(String keyword) {
        List<PhonebookVO> resultList = new ArrayList<>();
        String sql = "SELECT * FROM phonebook WHERE name LIKE ? OR hp LIKE ? OR memo LIKE ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String hp = rs.getString("hp");
                String memo = rs.getString("memo");
                
                PhonebookVO pb = new PhonebookVO(id, name, hp, memo);
                resultList.add(pb);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // 4. 선택 출력 기능 (selectById)
    public PhonebookVO selectById(int id) {
        String sql = "SELECT * FROM phonebook WHERE id = ?";
        PhonebookVO pb = null;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String hp = rs.getString("hp");
                String memo = rs.getString("memo");
                
                pb = new PhonebookVO(id, name, hp, memo);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pb;
    }

    // 5. 수정 기능 (update)
    public boolean update(PhonebookVO pb) {
        String sql = "UPDATE phonebook SET name = ?, hp = ?, memo = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getName());
            ps.setString(2, pb.getHp());
            ps.setString(3, pb.getMemo());
            ps.setInt(4, pb.getId());
            
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. 삭제 기능 (delete)
    public boolean delete(int id) {
        String sql = "DELETE FROM phonebook WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

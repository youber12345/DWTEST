package Main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

	    @Autowired
	    private PhonebookDAO phonebookDAO;

	    // 기본 맵핑 ("/")
	    @GetMapping("/")
	    public String home(Model model) {
	        // 전체 전화번호 출력
	        List<PhonebookVO> phonebookList = phonebookDAO.selectAll();
	        model.addAttribute("phonebookList", phonebookList);
	        return "phonebook";  // 기본적으로 phonebook.jsp를 반환
	    }

	    // 전화번호 추가 (insert)
	    @PostMapping("/addPhonebook")
	    public String addPhonebook(@RequestParam("name") String name, 
	                               @RequestParam("hp") String hp, 
	                               @RequestParam("memo") String memo) {
	        PhonebookVO pb = new PhonebookVO();
	        pb.setName(name);
	        pb.setHp(hp);
	        pb.setMemo(memo);
	        
	        phonebookDAO.insert(pb);
	        return "redirect:/";  // 전화번호 추가 후 홈으로 리다이렉트
	    }

	    // 검색 기능
	    @GetMapping("/searchPhonebook")
	    public String searchPhonebook(@RequestParam(value = "search", required = false) String search, Model model) {
	        List<PhonebookVO> phonebookList;
	        if (search != null && !search.isEmpty()) {
	            phonebookList = phonebookDAO.search(search);
	        } else {
	            phonebookList = phonebookDAO.selectAll();
	        }
	        model.addAttribute("phonebookList", phonebookList);
	        return "phonebook";
	    }

	    // 선택된 전화번호 정보 출력
	    @GetMapping("/selectPhonebook")
	    public String selectPhonebook(@RequestParam("id") int id, Model model) {
	        PhonebookVO selectedEntry = phonebookDAO.selectById(id);
	        model.addAttribute("selectedEntry", selectedEntry);
	        return "phonebook";
	    }

	    // 전화번호 수정 (update)
	    @PostMapping("/updatePhonebook")
	    public String updatePhonebook(@RequestParam("id") int id, 
	                                  @RequestParam("name") String name, 
	                                  @RequestParam("hp") String hp, 
	                                  @RequestParam("memo") String memo) {
	        PhonebookVO pb = new PhonebookVO();
	        pb.setId(id);
	        pb.setName(name);
	        pb.setHp(hp);
	        pb.setMemo(memo);
	        
	        phonebookDAO.update(pb);
	        return "redirect:/";  // 수정 후 홈으로 리다이렉트
	    }

	    // 전화번호 삭제 (delete)
	    @PostMapping("/deletePhonebook")
	    public String deletePhonebook(@RequestParam("id") int id) {
	        phonebookDAO.delete(id);
	        return "redirect:/";  // 삭제 후 홈으로 리다이렉트
	    }
	

}

package com.fisa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisa.exception.DeptNotFoundException;
import com.fisa.model.dao.DeptCopyRepository;
import com.fisa.model.domain.entity.DeptCopy;

@RestController
public class DeptCopyController {

	//DAO를 멤버로 선언 및 자동 초기화
	@Autowired
	private DeptCopyRepository dao;   
	
	@GetMapping("deptone")
	public DeptCopy getDept(int deptno) throws Exception {
		System.out.println("************* " + deptno);
		Optional<DeptCopy> dept = dao.findById(deptno);
		System.out.println(dept); 
		
		dept.orElseThrow(Exception::new);  
		
		return dept.get();	
	}
	
	//http://127.0.0.1:9989/guestbook/deptall
	@GetMapping("deptall")
	public Iterable<DeptCopy> getDeptAll(){
		//System.out.println(dao.findAll());		
		return dao.findAll();
	}
	
	
	@GetMapping("deptdelete")
	public String deleteDept(int deptno) throws DeptNotFoundException {
		dao.findById(deptno).orElseThrow(DeptNotFoundException::new);//로직 중지
		dao.deleteById(deptno);//존재할 경우실행되는 라인
		return "delete 성공";
	}
	
	@ExceptionHandler(DeptNotFoundException.class)
	public String exHandler(DeptNotFoundException e) {
		e.printStackTrace();		
		return "해당 부서 번호는 존재하지 않습니다.";
	}
		
	//예외 전담 처리 메소드
	@ExceptionHandler
	public String exHandler(Exception e) {
		e.printStackTrace();		
		return "요청시 입력 데이터 재 확인 부탁합니다";
	}
	
	
}

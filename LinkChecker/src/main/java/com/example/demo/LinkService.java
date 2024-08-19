package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class LinkService {
    @Autowired
	LinkRepo linkRepo;
	public LinkCheckModel create(LinkCheckModel linkCheckModel) {
		// TODO Auto-generated method stub
		return linkRepo.save(linkCheckModel);
	}
	public List<LinkCheckModel> findAll() {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		return linkRepo.findAll(sort);
	}

}

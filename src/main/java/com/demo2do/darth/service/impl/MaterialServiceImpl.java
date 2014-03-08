package com.demo2do.darth.service.impl;

import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.darth.entity.material.Material;
import com.demo2do.darth.service.MaterialService;

@Service("MaterialService")
public class MaterialServiceImpl extends GenericServiceImpl<Material> implements
		MaterialService {

}

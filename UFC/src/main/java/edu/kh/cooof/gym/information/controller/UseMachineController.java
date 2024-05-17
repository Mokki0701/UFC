package edu.kh.cooof.gym.information.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.cooof.gym.information.model.dto.UseMachine;
import edu.kh.cooof.gym.information.model.service.UseMachineService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/gym/information")
@RequiredArgsConstructor
public class UseMachineController {

    private final UseMachineService service;

    @GetMapping("/useMachines")
    public String getAllUseMachines(Model model, 
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "12") int size) {
        List<UseMachine> useMachinesList = service.getAllUseMachines();
        Map<String, Object> result = new HashMap<>();
        
        int start = Math.min(page * size, useMachinesList.size());
        int end = Math.min(start + size, useMachinesList.size());
        List<UseMachine> paginatedList = useMachinesList.subList(start, end);
        
        result.put("useMachines", paginatedList);
        result.put("totalItems", useMachinesList.size());
        result.put("currentPage", page);
        result.put("pageSize", size);

        model.addAttribute("result", result);
        return "gym/information/UseMachines";
    }

    @GetMapping("/useMachine/{id}")
    public String getUseMachineById(@PathVariable("id") int id, Model model) {
        UseMachine useMachine = service.getUseMachineById(id);
        model.addAttribute("useMachine", useMachine);
        return "gym/information/UseMachineDetail";
    }
}

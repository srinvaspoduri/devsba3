package in.political.party.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.political.party.dto.DevelopmentDto;
import in.political.party.dto.LeaderDevelopmentDto;
import in.political.party.exceptions.DevelopmentNotFoundException;
import in.political.party.exceptions.InvalidDataException;
import in.political.party.exceptions.LeaderIdNotFoundException;
import in.political.party.service.DevelopmentService;



@RestController
@RequestMapping("/politics/api/v1/development")
public class DevelopmentController {

	@Autowired
	private DevelopmentService developmentService;

	@PostMapping("/add-development")
	public ResponseEntity<DevelopmentDto> addDevelopments(@Valid @RequestBody DevelopmentDto developmentDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
		{

			throw new InvalidDataException("Invalid data Recived to register new Leader");
		}
		DevelopmentDto createDevelopment = developmentService.createDevelopment(developmentDto);
		return ResponseEntity.ok(createDevelopment);


	}
	@PutMapping("/update-development")
	public ResponseEntity<DevelopmentDto> updateDevelopments(@Valid @RequestBody DevelopmentDto developmentDto,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new InvalidDataException("Invalid data Recived to update the development");
		}
		DevelopmentDto updateDevelopment = developmentService.updateDevelopment(developmentDto);
		if(updateDevelopment!=null)
		{
			return ResponseEntity.ok(updateDevelopment);
		}
		else
		{
			throw new DevelopmentNotFoundException("No Developments found /Leader not present");
		}

	}

	@GetMapping("get-development-by/{leaderId}")
	public ResponseEntity<LeaderDevelopmentDto> getAllDevByLeader(@PathVariable Long leaderId) {

		LeaderDevelopmentDto devByLeader = developmentService.getDevByLeader(leaderId);
		if(devByLeader.getLeader().getPoliticalLeaderId()==null)
		{
			throw new LeaderIdNotFoundException("Leader not present to get development details");
		}
		if(!devByLeader.getDevelopmentDtos().isEmpty())
		{
			return ResponseEntity.ok(devByLeader);
		}
		else
		{
			throw new DevelopmentNotFoundException("No Developments found /Leader not present");
		}


	}

}

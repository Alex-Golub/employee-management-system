package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.io.entity.GeneralDetails;
import edu.mrdrprof.app.repository.GeneralDetailsRepository;
import edu.mrdrprof.app.service.GeneralDetailsService;
import edu.mrdrprof.app.shared.GeneralDetailsDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 12:23 PM
 */
@Service
@AllArgsConstructor
public class GeneralDetailsServiceImpl implements GeneralDetailsService {
  private final GeneralDetailsRepository generalDetailsRepository;
  private final ModelMapper mapper;

  @Override
  public GeneralDetailsDto getEmployeeGeneralDetails(String detailsId) {
    GeneralDetails details = generalDetailsRepository.findGeneralDetailsByPublicId(detailsId);
    if (details == null)
      throw new RuntimeException("There is no such details with id " + detailsId);

    return mapper.map(details, GeneralDetailsDto.class);
  }
}

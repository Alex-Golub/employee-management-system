package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.io.entity.Spouse;
import edu.mrdrprof.app.repository.SpouseRepository;
import edu.mrdrprof.app.service.SpouseService;
import edu.mrdrprof.app.shared.SpouseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 12:38 PM
 */
@Service
@AllArgsConstructor
public class SpouseServiceImpl implements SpouseService {
  private final SpouseRepository spouseRepository;
  private final ModelMapper mapper;

  @Override
  public SpouseDto getSpouse(String id) {
    Spouse spouse = spouseRepository.findSpouseByPublicId(id);
    if (spouse == null)
      throw new RuntimeException("Spouse with the id is not found");

    return mapper.map(spouse, SpouseDto.class);
  }
}

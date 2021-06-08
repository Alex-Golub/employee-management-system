package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.io.entity.Child;
import edu.mrdrprof.app.repository.ChildRepository;
import edu.mrdrprof.app.shared.ChildDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 3:09 PM
 */
@Service
@AllArgsConstructor
public class ChildServiceImpl implements ChildService {
  private final ChildRepository childRepository;
  private final ModelMapper mapper;

  @Override
  public ChildDto getChild(String childId) {
    Child child = childRepository.findChildByPublicId(childId);
    if (child == null)
      throw new RuntimeException("Child not found"); // FIXME

    return mapper.map(child, ChildDto.class);
  }

  @Override
  public List<ChildDto> getChildren(String empId) {
    List<Child> children = childRepository.findChildrenByEmployee_PublicId(empId);
    if (children == null)
      throw new RuntimeException("No children found");

    return mapper.map(children, new TypeToken<List<ChildDto>>() {}.getType());
  }
}

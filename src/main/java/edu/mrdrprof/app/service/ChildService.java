package edu.mrdrprof.app.service;

import edu.mrdrprof.app.shared.ChildDto;

import java.util.List;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 3:08 PM
 */
public interface ChildService {
  ChildDto getChild(String childId);
  List<ChildDto> getChildren(String empId);
}

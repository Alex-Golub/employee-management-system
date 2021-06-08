package edu.mrdrprof.app.service;

import edu.mrdrprof.app.shared.SpouseDto;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 12:37 PM
 */
public interface SpouseService {
  SpouseDto getSpouse(String id);
}

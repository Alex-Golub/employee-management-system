package edu.mrdrprof.app.service;

import edu.mrdrprof.app.shared.GeneralDetailsDto;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 12:22 PM
 */
public interface GeneralDetailsService {
  GeneralDetailsDto getEmployeeGeneralDetails(String detailsId);
}

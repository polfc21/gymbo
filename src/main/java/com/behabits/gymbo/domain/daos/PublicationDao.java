package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Publication;

public interface PublicationDao {
    Publication savePublication(Publication publication);
}

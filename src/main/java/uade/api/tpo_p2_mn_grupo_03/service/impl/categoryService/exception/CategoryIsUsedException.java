package uade.api.tpo_p2_mn_grupo_03.service.impl.categoryService.exception;

import uade.api.tpo_p2_mn_grupo_03.exception.BadRequestException;

//La categoria ya esta en uso
public class CategoryIsUsedException extends BadRequestException{

    public CategoryIsUsedException() {
        super("La Categoria esta siendo usada por uno o más productos");
    }
}

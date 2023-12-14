import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';

export class UserValidatorService {
  urlImageMaxSize(maxPixels: number): AsyncValidatorFn {
    return async (control: AbstractControl): Promise<ValidationErrors | null> => {
      const imgUrl = control.value;

      if (!imgUrl) {
        return null;
      }

      const loadImage = (): Promise<void> =>
        new Promise((resolve, reject) => {
          const img = new Image();

          img.src = imgUrl;

          img.onload = function (event) {
            const loadedImage = event.currentTarget as HTMLImageElement;
            const pixelsHeight = loadedImage.height;
            const pixelsWidth = loadedImage.width;
            const sizeValid = pixelsHeight <= maxPixels && pixelsWidth <= maxPixels;

            if (sizeValid) {
              resolve();
            } else {
              reject({ maxImageSize: true, maxPixels, pixelsHeight, pixelsWidth } as ValidationErrors);
            }
          };

          img.onerror = function () {
            reject({ loadImageError: true } as ValidationErrors);
          };
        });

      try {
        await loadImage();
        return null;
      } catch (error) {
        console.error('Image loading error:', error);
        return error as ValidationErrors;
      }
    };
  }
}

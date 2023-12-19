import { Pipe, PipeTransform } from '@angular/core';

import dayjs from 'dayjs/esm';

@Pipe({
  name: 'gsDatetimeChat',
})
export class GsDatetimeChatPipe implements PipeTransform {
  transform(day: dayjs.Dayjs | null | undefined): string {
    if (day?.isAfter(dayjs().subtract(7, 'day'))) {
      return day.fromNow();
    }

    return day ? day.format('D MMMM YYYY - HH:mm:ss') : '';
  }
}

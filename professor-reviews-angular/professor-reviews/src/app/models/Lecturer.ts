import { Institute } from './Institute';
import { Review } from './Review';

export class Lecturer {
    id: number;
    fullname: string;
    email: string;
    img_src: string;
    ratesCount: number;
    institute_id: number;
    institute: Institute; 
    averageRate: number;
    reviews: Array<Review>;
}
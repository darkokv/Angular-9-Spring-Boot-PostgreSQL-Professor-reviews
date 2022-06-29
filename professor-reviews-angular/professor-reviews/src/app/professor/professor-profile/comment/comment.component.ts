import { Component, Input, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import * as $ from 'jquery';
import { ToastrService } from 'ngx-toastr';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {
  @ViewChild('closebutton') closebutton;
  
  @Input() user_id: number;
  @Input() role: string;
  @Input() review_id: number;
  @Input() useful_l: number;
  @Input() interesting_l: number;
  @Input() material: number;
  @Input() commitment: number;
  @Input() communication: number;
  @Input() date: string;
  @Input() comment: string;
  @Input() likes: number;
  @Input() dislikes: number;
  @Input() deleteModal: string;
  //@Input() isVoted: boolean;

  @Output() refresh = new EventEmitter();

  revLike: any;
  //user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : 0;

  constructor(private service: ReviewService,  private toastr: ToastrService) { }

  ngOnInit(): void {
  }



  //setGreenColor(event) {
  //  event.target.style.color = "green";
  //}
  //setRedColor(event) {
  //  event.target.style.color = "red";
  //}

  likeReview() {
    var reviewLike = {
      user_id: this.user_id,
      review_id: this.review_id,
      likeinput: true,
      dislikeinput: false
    }

    if(this.user_id) {
      this.service.selectReviewLike(this.user_id, this.review_id).subscribe(
        (res) => {
          this.revLike = res;
          //chech if like exist
          if(res == null) {
            this.like(reviewLike);
          }
          else if(res != null) {
            if(this.revLike.likeinput == false) {
              this.update(reviewLike);
            }
            else if(this.revLike.likeinput == true){
              this.delete(this.user_id, this.review_id);
            }
          }
        })
        
        this.refresh.emit();
    }
    else {
      this.toastr.warning("Morate se prijaviti da biste ocenili recenziju.");
    }

  }

  dislikeReview() {
    var reviewDislike = {
      user_id: this.user_id,
      review_id: this.review_id,
      likeinput: false,
      dislikeinput: true
    }

    if(this.user_id) {
      this.service.selectReviewLike(this.user_id, this.review_id).subscribe(
        (res) => {
          this.revLike = res;
          //chech if like exist
          if(res == null) {
            this.like(reviewDislike);
          }
          else if(res != null) {
            if(this.revLike.dislikeinput == false) {
              this.update(reviewDislike);
            }
            else if(this.revLike.dislikeinput == true){
              this.delete(this.user_id, this.review_id);
            }
          }
        })

        this.refresh.emit();
    }
    else {
      this.toastr.warning("Morate se prijaviti da biste ocenili recenziju.");
    }
      
  }

  like(reviewLike) {
    this.service.insertReviewLike(reviewLike).subscribe(
      (res) => {
        if(res != 1) {
          this.toastr.error("Neuspešno !");
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  update(reviewUpdate) {
    this.service.updateReviewLike(reviewUpdate).subscribe(
      (res) => {
        if(res != 1) {
          this.toastr.error("Neuspešno !");
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  delete(userId, reviewId) {
    this.service.deleteReviewLike(userId, reviewId).subscribe(
      (res) => {
        if(res != 1) {
          this.toastr.error("Neuspešno !");
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteReview(id) {
    if(this.role == 'admin') {
      this.service.deleteReview(id).subscribe(
        (res) => {
          if(res == 1) {
            this.closebutton.nativeElement.click();
            this.refresh.emit();
            this.toastr.success("Uspešno ste obrisali recenziju!");
          }
          else {
            this.toastr.error("Neuspešno brisanje recenzije!");
          }
        },
        err => {
          console.log(err);
        }
      )
    }
    else {
      this.toastr.warning("Nemate dozvolu za pristup mrežnog resursa!");
    }
  }



}

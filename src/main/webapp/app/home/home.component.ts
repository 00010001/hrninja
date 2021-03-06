import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LoginModalService, Principal, Account } from 'app/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/filter';
import { IJobAd } from 'app/shared/model/job-ad.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {

    stanowisko: string;
    miejscowosc: string;
    pracodawca: string;

    resp: IJobAd[];

    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient
    ) { }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    search(stanowisko, miejscowosc, pracodawca) {
        this.http.post<IJobAd[]>('http://localhost:8080/search', {
      position: stanowisko,
      location: miejscowosc,
      company: pracodawca
    })
      .subscribe(
        res => {
          this.resp = res;
          console.log(this.resp);

        },
        err => {
          console.log('Error occured');
        }
      );
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}

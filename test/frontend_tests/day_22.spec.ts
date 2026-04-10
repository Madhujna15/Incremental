import { ComponentFixture, TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { AuthService } from "../app/auth/services/auth.service";
import { environment } from "../environments/environment";
import { Supplier } from "../app/supplylink/types/Supplier"; 
import { LoginComponent } from "../app/auth/components/login/login.component";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthModule } from "../app/auth/auth.module";
import { ActivatedRoute, Router } from "@angular/router";
import { of } from "rxjs";
import { UserComponent } from "../app/auth/components/user/user.component";

describe("AuthService", () => {
  let authService: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    authService = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should be created", () => {
    expect(authService).toBeTruthy();
  });

  describe("login", () => {
    it("should make a POST request to login", () => {
      const user: Supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");

      authService.login(user).subscribe((result) => {
        expect(result).not.toBeNull();
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/user/login`);
      expect(req.request.method).toBe("POST");
      expect(req.request.body).toEqual(user);

      req.flush(user);
    });
  });

  describe("getToken", () => {
    it("should get the token from localStorage", () => {
      const token = "testtoken";
      spyOn(localStorage, "getItem").and.returnValue(token);

      const result = authService.getToken();

      expect(localStorage.getItem).toHaveBeenCalledWith("token");
      expect(result).toEqual(token);
    });
  });

  describe("createUser", () => {
    it("should make a POST request to create a user", () => {
      const user: Supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");
      const response: Supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");

      authService.createUser(user).subscribe((result) => {
        expect(result).toEqual(response);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/user/register`);
      expect(req.request.method).toBe("POST");
      expect(req.request.body).toEqual(user);

      req.flush(response);
    });
  });
});

describe("LoginComponent", () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj("AuthService", ["login"]);
    const routerSpy = jasmine.createSpyObj("Router", ["navigate"]);

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy },
        FormBuilder,
      ],
    }).compileComponents();

    // Important: Move this here to avoid issues with duplicate instantiation
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  describe("ngOnInit", () => {
    it("should initialize the userForm with empty fields and validators", () => {
      component.ngOnInit();
      const form = component.loginForm;
      expect(form.get("username")?.value).toEqual("");
      expect(form.get("password")?.value).toEqual("");
    });
  });

  describe("onSubmit", () => {
    it("should not call authService.login if the form is invalid", () => {
      component.loginForm = formBuilder.group({
        username: ["", Validators.required],
        password: ["", Validators.required],
      });
      component.onSubmit();
      expect(authService.login).not.toHaveBeenCalled();
    });

    it("should call authService.login with the login data if the form is valid", () => {
      authService.login.and.returnValue(of({ token: "testToken" }));
      component.loginForm = formBuilder.group({
        username: ["testUser", Validators.required],
        password: ["testPassword", Validators.required],
      });
      component.onSubmit();
      expect(authService.login).toHaveBeenCalledWith({
        username: "testUser",
        password: "testPassword",
      });
    });
  });
});

describe("UserComponent", () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let authService: AuthService;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, AuthModule],
      providers: [
        AuthService,
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => "john",
              },
            },
          },
        },
      ],
    }).compileComponents();

    // Move this here to avoid errors with duplicate TestBed instantiation
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  describe("ngOnInit", () => {
    it("should initialize the userForm with empty fields and validators", () => {
      component.ngOnInit();
      const form = component.userForm;
      expect(form.get("username")?.value).toEqual("");
      expect(form.get("password")?.value).toEqual("");
    });
  });
});

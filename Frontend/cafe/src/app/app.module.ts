import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ErrorComponent } from './shared/error/error.component';
import { LoaderComponent } from './shared/loader/loader.component';
import { HomeComponent } from './pages/home/home.component';
import { CafeComponent } from './pages/cafe/cafe.component';
import { LoginFormComponent } from './pages/home/components/login-form/login-form.component';
import { SignupFormComponent } from './pages/home/components/signup-form/signup-form.component';
import { CategoryDialogComponent } from './pages/cafe/components/category-dialog/category-dialog.component';
import { ChangePasswordComponent } from './pages/cafe/components/change-password/change-password.component';
import { DashboardComponent } from './pages/cafe/components/dashboard/dashboard.component';
import { DeleteDialogComponent } from './pages/cafe/components/delete-dialog/delete-dialog.component';
import { LogoutComponent } from './pages/cafe/components/logout/logout.component';
import { ManageCategoryComponent } from './pages/cafe/components/manage-category/manage-category.component';
import { ManageOrderComponent } from './pages/cafe/components/manage-order/manage-order.component';
import { ManageProductComponent } from './pages/cafe/components/manage-product/manage-product.component';
import { ManageUserComponent } from './pages/cafe/components/manage-user/manage-user.component';
import { ProductDialogComponent } from './pages/cafe/components/product-dialog/product-dialog.component';
import { ViewBillProductComponent } from './pages/cafe/components/view-bill-product/view-bill-product.component';
import { ViewBillComponent } from './pages/cafe/components/view-bill/view-bill.component';

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    LoaderComponent,
    HomeComponent,
    CafeComponent,
    LoginFormComponent,
    SignupFormComponent,
    CategoryDialogComponent,
    ChangePasswordComponent,
    DashboardComponent,
    DeleteDialogComponent,
    LogoutComponent,
    ManageCategoryComponent,
    ManageOrderComponent,
    ManageProductComponent,
    ManageUserComponent,
    ProductDialogComponent,
    ViewBillProductComponent,
    ViewBillComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

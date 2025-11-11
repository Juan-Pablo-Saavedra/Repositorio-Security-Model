import { Component, OnInit, ViewChild, HostListener, OnDestroy } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, NavigationEnd } from '@angular/router';
import { AuthService } from './shared/services/auth.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, Subject, map, shareReplay, filter, takeUntil } from 'rxjs';

interface MenuItem {
  id: string;
  label: string;
  icon: string;
  route: string;
  badge?: number;
  children?: MenuItem[];
}

interface Notification {
  id: number;
  type: 'success' | 'warning' | 'error' | 'info';
  message: string;
  time: Date;
}

interface Breadcrumb {
  label: string;
  route?: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  
  title = 'InventarioPro';
  isAuthenticated = false;
  currentUser: any = null;
  isDarkTheme = false;
  searchQuery = '';
  notificationCount = 0;
  
  isCollapsed$: Observable<boolean>;
  isHandset$: Observable<boolean>;

  notifications: Notification[] = [
    { 
      id: 1, 
      type: 'warning', 
      message: 'Stock bajo: Producto XYZ tiene solo 5 unidades disponibles', 
      time: new Date(Date.now() - 1000 * 60 * 15)
    },
    { 
      id: 2, 
      type: 'info', 
      message: 'Nueva orden de compra #ORD-2024-001 recibida correctamente', 
      time: new Date(Date.now() - 1000 * 60 * 30)
    },
    { 
      id: 3, 
      type: 'success', 
      message: 'Inventario actualizado: Se agregaron 100 unidades de Producto ABC', 
      time: new Date(Date.now() - 1000 * 60 * 60)
    },
    { 
      id: 4, 
      type: 'error', 
      message: 'Alerta: Producto DEF ha alcanzado el stock mínimo crítico', 
      time: new Date(Date.now() - 1000 * 60 * 90)
    }
  ];

  breadcrumbs: Breadcrumb[] = [
    { label: 'Dashboard', route: '/dashboard' }
  ];

  menuItems: MenuItem[] = [
    {
      id: 'dashboard',
      label: 'Dashboard',
      icon: 'dashboard',
      route: '/dashboard'
    },
    {
      id: 'inventory',
      label: 'Inventario',
      icon: 'inventory_2',
      route: '/inventory',
      badge: 5
    },
    {
      id: 'products',
      label: 'Productos',
      icon: 'category',
      route: '/products'
    },
    {
      id: 'categories',
      label: 'Categorías',
      icon: 'label',
      route: '/categories'
    },
    {
      id: 'suppliers',
      label: 'Proveedores',
      icon: 'local_shipping',
      route: '/suppliers'
    },
    {
      id: 'orders',
      label: 'Órdenes',
      icon: 'shopping_cart',
      route: '/orders',
      badge: 12
    }
  ];

  @ViewChild('sidenav') sidenav: any;

  constructor(
    private authService: AuthService,
    private router: Router,
    private breakpointObserver: BreakpointObserver
  ) {
    this.isHandset$ = this.breakpointObserver.observe([Breakpoints.Handset, Breakpoints.Tablet])
      .pipe(
        map(result => result.matches),
        shareReplay(1)
      );

    this.isCollapsed$ = this.isHandset$;
  }

  ngOnInit(): void {
    this.initializeAuth();
    this.initializeTheme();
    this.initializeRouterEvents();
    this.updateNotificationCount();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private initializeAuth(): void {
    this.authService.isAuthenticated$
      .pipe(takeUntil(this.destroy$))
      .subscribe(isAuth => {
        this.isAuthenticated = isAuth;
        if (!isAuth && !this.router.url.includes('/auth')) {
          this.router.navigate(['/auth/login']);
        }
      });

    this.authService.currentUser$
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        this.currentUser = user;
      });
  }

  private initializeTheme(): void {
    const savedTheme = localStorage.getItem('inventario_theme');
    if (savedTheme) {
      this.isDarkTheme = savedTheme === 'dark';
    } else {
      const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
      this.isDarkTheme = prefersDark;
    }
    this.applyTheme();
  }

  private initializeRouterEvents(): void {
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe((event: any) => {
        this.updateBreadcrumbs(event.url);
        this.isHandset$.pipe(takeUntil(this.destroy$)).subscribe(isHandset => {
          if (isHandset && this.sidenav) {
            this.sidenav.close();
          }
        });
      });
  }

  private updateBreadcrumbs(url: string): void {
    const segments = url.split('/').filter(segment => segment);
    this.breadcrumbs = [{ label: 'Dashboard', route: '/dashboard' }];

    const routeMap: { [key: string]: string } = {
      'inventory': 'Inventario',
      'products': 'Productos',
      'categories': 'Categorías',
      'suppliers': 'Proveedores',
      'orders': 'Órdenes',
      'settings': 'Configuración',
      'profile': 'Perfil'
    };

    segments.forEach((segment, index) => {
      if (routeMap[segment]) {
        const route = '/' + segments.slice(0, index + 1).join('/');
        this.breadcrumbs.push({
          label: routeMap[segment],
          route: index < segments.length - 1 ? route : undefined
        });
      }
    });
  }

  private updateNotificationCount(): void {
    this.notificationCount = this.notifications.length;
  }

  toggleSidebar(): void {
    if (this.sidenav) {
      this.sidenav.toggle();
    }
  }

  toggleTheme(): void {
    this.isDarkTheme = !this.isDarkTheme;
    this.applyTheme();
    localStorage.setItem('inventario_theme', this.isDarkTheme ? 'dark' : 'light');
  }

  private applyTheme(): void {
    const root = document.documentElement;
    if (this.isDarkTheme) {
      root.setAttribute('data-theme', 'dark');
    } else {
      root.removeAttribute('data-theme');
    }
  }

  performSearch(): void {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/inventory'], { 
        queryParams: { search: this.searchQuery.trim() } 
      });
      this.searchQuery = '';
    }
  }

  getNotificationIcon(type: string): string {
    const icons: { [key: string]: string } = {
      'success': 'check_circle',
      'warning': 'warning',
      'error': 'error',
      'info': 'info'
    };
    return icons[type] || 'notifications';
  }

  clearAllNotifications(): void {
    this.notifications = [];
    this.notificationCount = 0;
  }

  removeNotification(id: number): void {
    this.notifications = this.notifications.filter(n => n.id !== id);
    this.updateNotificationCount();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    // El BreakpointObserver maneja esto automáticamente
  }

  navigateToProfile(): void {
    this.router.navigate(['/profile']);
  }

  navigateToSettings(): void {
    this.router.navigate(['/settings']);
  }
}
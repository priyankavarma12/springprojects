export class Visitor {
    visitorId: number;
    adminId: number;
    firstName: string;
    lastName: string;
    email: string;
    primaryCountryCode: string;
    primaryPhNumber: string;
    secondaryCountryCode: string;
    secondaryPhNumber: string;
    verificationEmailSent: boolean;
    nationalId: string;
    companyName?: string;
    activated: boolean
    invitations?: []
}
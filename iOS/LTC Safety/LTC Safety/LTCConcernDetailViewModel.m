//
//  LTCConcernDetailViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Modified by Daniel Morris
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernDetailViewModel.h"
#import "LTCClientApi.h"

// The prompt keys that map to a string in the Localizable.strings
NSString *const LTCDetailContactInfoTitle               = @"CONTACT_INFO_TITLE";            //Contact Information
NSString *const LTCDetailConcernInfoTitle               = @"DETAIL_CONCERN_INFO_TITLE";     //Concern Information
NSString *const LTCDetailConcernStatusLog               = @"DETAIL_STATUS_LOG";             //Concern Status Log
NSString *const LTCRetractConcernPrompt                 = @"RETRACT_CONCERN_PROMPT";        //Retract

NSString *const LTCDetailNameTitle                      = @"DETAIL_NAME_TITLE";             //Name
NSString *const LTCDetailEmailTitle                     = @"DETAIL_EMAIL_TITLE";            //Email
NSString *const LTCDetailPhoneNumberTitle               = @"DETAIL_PHONE_NUMBER_TITLE";     //Phone Number
NSString *const LTCDetailConcernNatureTitle             = @"DETAIL_CONCERN_NATURE_TITLE";   //Concern Nature
NSString *const LTCDetailFacilityTitle                  = @"DETAIL_FACILITY_TITLE";         //Facility Name
NSString *const LTCDetailRoomTitle                      = @"DETAIL_ROOM_TITLE";             //Room
NSString *const LTCDetailActionsTakenTitle              = @"DETAIL_ACTIONS_TAKEN_TITLE";    //Actions Taken
NSString *const LTCDetailDescriptionTitle               = @"DETAIL_DESCRIPTION_TITLE";      //Concern Description

// The row desriptors for unique identification
NSString *const LTCDetailDescriptorRetractConcern       = @"RETRACT_CONCERN";
NSString *const LTCDetailDescriptorReporterName         = @"REPORTER_NAME";
NSString *const LTCDetailDescriptorPhoneNumber          = @"PHONE_NUMBER";
NSString *const LTCDetailDescriptorEmail                = @"EMAIL";
NSString *const LTCDetailDescriptorConcernNature        = @"CONCERN_NATURE";
NSString *const LTCDetailDescriptorFacilityName         = @"FACILITY_NAME";
NSString *const LTCDetailDescriptorRoomNumber           = @"ROOM_NUMBER";
NSString *const LTCDetailDescriptorActionsTaken         = @"ACTIONS_TAKEN";
NSString *const LTCDetailDescriptorDescription          = @"DESCRIPTION";
NSString *const LTCDetailDescriptorStatus               = @"CONCERN_STATUS";

@interface LTCConcernDetailViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (readwrite, nonatomic, strong) LTCConcern *concern;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorReporterName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorPhoneNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorEmail;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernNature;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorFacilityName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorRoomNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorActionsTaken;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorDescription;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernStatus;
@end

@implementation LTCConcernDetailViewModel
@dynamic testHook_descriptorReporterName;
@dynamic testHook_descriptorPhoneNumber;
@dynamic testHook_descriptorEmail;
@dynamic testHook_descriptorConcernNature;
@dynamic testHook_descriptorFacilityName;
@dynamic testHook_descriptorRoomNumber;
@dynamic testHook_descriptorActionsTaken;
@dynamic testHook_descriptorDescription;
@dynamic testHook_descriptorConcernStatus;

#pragma mark - Test Hooks

/**
 A test took for getting the reporter name descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorReporterName].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorReporterName {
    return LTCDetailDescriptorReporterName;
}

/**
 A test took for getting the phone number descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorPhoneNumber].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorPhoneNumber {
    return LTCDetailDescriptorPhoneNumber;
}

/**
 A test took for getting the email descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorEmail].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorEmail {
    return LTCDetailDescriptorEmail;
}

/**
 A test took for getting the email descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorConcernNature].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorConcernNature {
    return LTCDetailDescriptorConcernNature;
}

/**
 A test took for getting the facility name descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorFacilityName].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorFacilityName {
    return LTCDetailDescriptorFacilityName;
}

/**
 A test took for getting the room number descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorRoomNumber].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorRoomNumber {
    return LTCDetailDescriptorRoomNumber;
}

/**
 A test took for getting the actions taken descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorActionsTaken].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorActionsTaken {
    return LTCDetailDescriptorActionsTaken;
}

/**
 A test took for getting the actions taken descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorDescription].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorDescription {
    return LTCDetailDescriptorDescription;
}
/**
 A test took for getting the actions taken descriptor to programmatically set the LTCNewConcernViewModel's concern data without going through the LTCNewViewController class.
 @code
 [viewModel formRowWithTag:viewModel.testHook_descriptorActionsTaken].value = @"...";
 @endcode
 
 @return The descriptor string.
 */
- (NSString *)testHook_descriptorConcernStatus {
    return LTCDetailDescriptorStatus;
}

#pragma mark - Implementation

- (instancetype)initWithConcern:(LTCConcern *)concern {
    
    NSAssert(concern != nil, @"Attempted to initialize the detail concern view model with a nil concern.");
    if (self = [super init]) {
        
        self.clientApi = [[LTCClientApi alloc] init];
        self.concern = concern;
        
        // Sets up the form contact information section
        [self addFormSection:[self createContactInfoSection:concern]];
        
        // Sets up the concern information section
        [self addFormSection:[self createConcernInfoSection:concern]];

        // Sets up the concern description section
        if(concern.descriptionProperty != nil){
            [self addFormSection:[self createDescriptionSection:concern]];
        }
        
        // Sets up the actions taken section
        if(concern.actionsTaken != nil){
            [self addFormSection:[self createActionsTakenSection:concern]];
        }
        
        // Sets up the concern status log
        [self addFormSection:[self createStatusLogSection:concern]];

        // Sets up the actions retract button section
        if(!([concern.statuses.lastObject.concernType isEqualToString: @"RETRACTED"] || [concern.statuses.lastObject.concernType isEqualToString: @"RESOLVED"])){
            [self addFormSection:[self createRetractButtonSection:concern]];
        }
    }
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    NSAssert(self.clientApi != nil, @"Client API was nil after the detail concern view model initializer finished.");
    
    return self;
}

- (XLFormSectionDescriptor *)createContactInfoSection:(LTCConcern *)concern{

    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;

    section.title = NSLocalizedString(LTCDetailContactInfoTitle, nil);
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorReporterName
                                                rowType:XLFormRowDescriptorTypeInfo
                                                  title:NSLocalizedString(LTCDetailNameTitle, nil)];
    row.value = concern.reporter.name;
    [section addFormRow:row];
    
    if(concern.reporter.email != nil){
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorEmail
                                                    rowType:XLFormRowDescriptorTypeInfo
                                                      title:NSLocalizedString(LTCDetailEmailTitle, nil)];
        row.value = concern.reporter.email;
        [section addFormRow:row];
    }
    
    if(concern.reporter.phoneNumber != nil){
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorPhoneNumber
                                                    rowType:XLFormRowDescriptorTypeInfo
                                                      title:NSLocalizedString(LTCDetailPhoneNumberTitle, nil)];
        row.value = concern.reporter.phoneNumber;
        [section addFormRow:row];
    }
    
    return section;
}

- (XLFormSectionDescriptor *)createConcernInfoSection:(LTCConcern *)concern{
    
    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;
    
    section.title = NSLocalizedString(LTCDetailConcernInfoTitle, nil);
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorConcernNature
                                                rowType:XLFormRowDescriptorTypeInfo
                                                  title:NSLocalizedString(LTCDetailConcernNatureTitle, nil)];
    row.value = concern.concernNature;
    [section addFormRow:row];
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorFacilityName
                                                rowType:XLFormRowDescriptorTypeInfo
                                                  title:NSLocalizedString(LTCDetailFacilityTitle, nil)];
    row.value = concern.location.facilityName;
    [section addFormRow:row];
    
    if(concern.location.roomName != nil){
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorRoomNumber
                                                    rowType:XLFormRowDescriptorTypeInfo
                                                      title:NSLocalizedString(LTCDetailRoomTitle, nil)];
        row.value = concern.location.roomName;
        [section addFormRow:row];
    }
    
    return section;
}

- (XLFormSectionDescriptor *)createActionsTakenSection:(LTCConcern *)concern{
    
    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;
    
    section.title = NSLocalizedString(LTCDetailActionsTakenTitle, nil);

    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorActionsTaken
                                                rowType:XLFormRowDescriptorTypeTextView];
    row.disabled = @YES;
    row.value = concern.actionsTaken;
    
    [section addFormRow:row];
    
    return section;
}

- (XLFormSectionDescriptor *)createDescriptionSection:(LTCConcern *)concern{
    
    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;
    
    section.title = NSLocalizedString(LTCDetailDescriptionTitle, nil);
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorDescription
                                                rowType:XLFormRowDescriptorTypeTextView];
    row.disabled = @YES;
    row.value = concern.descriptionProperty;
    
    [section addFormRow:row];
    
    return section;
}

- (XLFormSectionDescriptor *)createStatusLogSection:(LTCConcern *)concern{
    
    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;
    
    section.title = NSLocalizedString(LTCDetailConcernStatusLog, nil);
    
    NSString *dateString;
    for (LTCConcernStatus* status in concern.statuses){
        dateString = [NSDateFormatter localizedStringFromDate: status.creationDate
                                                    dateStyle:NSDateFormatterMediumStyle
                                                    timeStyle:NSDateFormatterShortStyle];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorStatus
                                                    rowType:XLFormRowDescriptorTypeInfo
                                                      title:NSLocalizedString(status.concernType,nil)];
        row.value = dateString;
        
        [section addFormRow:row];
    }
    
    return section;
}

- (XLFormSectionDescriptor *)createRetractButtonSection:(LTCConcern *)concern{
    
    XLFormSectionDescriptor *section;
    section = [XLFormSectionDescriptor formSection];
    XLFormRowDescriptor *row;
    
    row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDetailDescriptorRetractConcern
                                                rowType:XLFormRowDescriptorTypeButton
                                                  title:NSLocalizedString(LTCRetractConcernPrompt, nil)];
    [section addFormRow:row];
    
    return section;
}

/**
 Get the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 */
- (SEL)retractCallback {
    
    // Get the retract row discriptor's formSelector
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDetailDescriptorRetractConcern];
    NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDetailDescriptorRetractConcern);
    
    return descriptor.action.formSelector;
}

/**
 Set the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 @param retractCallback The callback that is called when the retract concern row is tapped
 */

- (void)setRetractCallback:(SEL)retractCallback {

    // Set the retract row discriptor's formSelector
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDetailDescriptorRetractConcern];
    //NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDetailDescriptorRetractConcern);
    
    descriptor.action.formSelector = retractCallback;
    
}

@end
